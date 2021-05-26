package com.example.flashcards.ui.group_card

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.flashcards.api.WebClient
import com.example.flashcards.databinding.GroupCardBinding
import com.example.flashcards.model.Token
import com.example.flashcards.model.cards.CardsResponse
import com.example.flashcards.model.history.HistoryCommit
import com.example.flashcards.model.history.UserHistoryRequest
import com.example.flashcards.model.history.UserHistoryStatus
import com.example.flashcards.model.user.UserRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class GroupCardFragment : Fragment() {
    private lateinit var groupCardViewModel: GroupCardViewModel
    private var _binding: GroupCardBinding? = null

    private val binding get() = _binding!!

    private val webClient = WebClient().getApi()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //--------------------------------------------//
        // Получение id группы и id и токена пользователя
        val userIdSave = context?.getSharedPreferences("UserId", Context.MODE_PRIVATE)
        val userTokenSave = context?.getSharedPreferences("UserToken", Context.MODE_PRIVATE)
        val groupIdSave = context?.getSharedPreferences("GroupId", Context.MODE_PRIVATE)
        var userToken = ""
        var userId = ""
        var groupId = ""

        if (userTokenSave?.contains("UserToken") == true) {
            userToken = userTokenSave.getString("UserToken", "").toString()
        }
        if (userIdSave?.contains("UserId") == true) {
            userId = userIdSave.getString("UserId", "").toString()
        }
        if (groupIdSave?.contains("GroupId") == true) {
            groupId = groupIdSave.getString("GroupId", "").toString()
        }
        //--------------------------------------------//

        //--------------------------------------------//
        // Получение общих данных и т.д.
        groupCardViewModel =
            ViewModelProvider(this).get(GroupCardViewModel::class.java)

        _binding = GroupCardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //--------------------------------------------//

        //--------------------------------------------//
        // Получение данных карточки
        val question: TextView = binding.cardQuestion

        val getCards = UserRequest(
            Token(userId.toLong(), userToken),
            groupId.toLong()
        )

        val callCards = webClient.getGroupCards(getCards)

        callCards.enqueue(object : Callback<CardsResponse> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<CardsResponse>, response: Response<CardsResponse>) {
                Log.i("test", response.body()?.records.toString())
                var randomQA = Random.nextInt(0, response.body()?.records!!.size)

                // Вывод вопроса на экран
                question.text = response.body()?.records?.get(randomQA)?.params?.get("question")

                // Получение ответа
                val answer = binding.cardAnswer.text

                // Обработка нажатия кнопки след. вопроса
                binding.nextQuestion.setOnClickListener {
                    randomQA = Random.nextInt(0, response.body()?.records!!.size)
                    question.text = response.body()?.records?.get(randomQA)?.params?.get("question")
                }

                // Обработка нажатия кнопки отправки ответа
                binding.submitAnswer.setOnClickListener {

                    //--------------------------------------------//
                    // Отправка ответа пользователя на сервер
                    val historyCommit = HistoryCommit(
                        answer.toString(), null, answer.toString().equals(
                            response.body()?.records?.get(randomQA)?.params?.get("answer")!!,
                            ignoreCase = true
                        ), response.body()?.records?.get(randomQA)!!.id
                    )
                    val userHistory = UserHistoryRequest(
                        Token(userId.toLong(), userToken),
                        listOf(historyCommit)
                    )
                    val putHistory = webClient.postUserHistory(userHistory)

                    putHistory.enqueue(object : Callback<UserHistoryStatus> {
                        override fun onResponse(
                            call: Call<UserHistoryStatus>,
                            response: Response<UserHistoryStatus>
                        ) {
                            Log.i("test", userHistory.toString())
                            Log.i("test", response.body().toString())
                        }

                        override fun onFailure(call: Call<UserHistoryStatus>, t: Throwable) {
                            Log.i("test", "error $t")
                        }
                    })
                    //--------------------------------------------//

                    if (answer.toString()
                            .equals(
                                response.body()?.records?.get(randomQA)?.params?.get("answer")!!,
                                ignoreCase = true
                            )
                    ) {
                        Toast.makeText(context, "Your answer is correct", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Your answer is incorrect", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

            override fun onFailure(call: Call<CardsResponse>, t: Throwable) {
                Log.i("test", "error $t")
            }
        })
        //--------------------------------------------//

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
