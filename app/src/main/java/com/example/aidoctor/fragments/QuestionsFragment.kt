package com.example.aidoctor.fragments

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.aidoctor.R
import com.example.aidoctor.ui.theme.AIDoctorTheme
import kotlinx.coroutines.delay


class QuestionsFragment : Fragment(R.layout.fragment_questions) {
    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ComposeView>(R.id.composeViewQuestions).setContent {
            AIDoctorTheme {
                QuestionAnswerScreen(::navigateToHomeFragment)
            }

        }
    }

    private fun navigateToHomeFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main, HomeFragment())
            .commit()
    }

}

@Preview(showBackground = true)
@Composable
fun QuestionAnswerScreenPreview() {
    QuestionAnswerScreen(onBackClicked = {})
}

@Composable
fun QuestionAnswerScreen(onBackClicked: () -> Unit) {

    val questions = listOf(
        "What is the first step in providing first aid?",
        "What is the recommended compression-to-breath ratio for CPR in adults?",
        "What is the correct hand placement for chest compressions during CPR on an adult?",
        "What is the first thing you should do when someone is choking?",
        "How do you recognize the signs of a heart attack?",
        "What is the correct method to control severe bleeding?",
        "How should you remove a small embedded object, such as a splinter?",
        "What should you do if someone is experiencing a seizure?",
        "What is the first step in caring for a burn?",
        "What is the recommended treatment for a nosebleed?",
        "What should you do if someone is experiencing a severe allergic reaction (anaphylaxis)?",
        "How do you treat a sprained ankle?",
        "What is the recommended treatment for a dislocated joint?",
        "What should you do if someone is experiencing a heatstroke?",
        "How do you treat a minor burn that does not involve broken skin?",
        "What is the recommended treatment for a snakebite?",
        "How should you care for a minor cut or wound?",
        "What is the recommended treatment for a foreign object in the eye?",
        "What should you do if someone is experiencing a severe asthma attack?",
        "How should you care for a broken bone or fracture before medical help arrives?",
        "What should you do if someone is having a diabetic emergency and is conscious?",
        "How should you care for a person with a suspected neck or spinal injury?",
        "What is the recommended treatment for a jellyfish sting?",
        "What should you do if someone is experiencing a severe allergic reaction to an insect bite or sting?"
    )

    val answers = listOf(
        "Assess the scene for safety",
        "30 compressions to 2 breaths",
        "Middle of the chest, between the nipples",
        "Perform back blows",
        "Chest pain or discomfort, shortness of breath, nausea",
        "Apply pressure directly to the wound with a clean cloth or your hand",
        "Use a clean needle to gently lift it out",
        "Clear the area around them of any objects that could cause injury",
        "Apply a cold compress or immerse the burn in cool water",
        "Pinch the nostrils together and lean forward slightly",
        "Administer an epinephrine auto-injector if available and call emergency services",
        "Elevate the injured limb and apply ice wrapped in a cloth",
        "Immobilize the joint and seek medical attention",
        "Move them to a cool place, remove excess clothing, and apply cool, wet towels or ice packs",
        "Rinse the burn with cold water and cover it with a sterile dressing",
        "Immobilize the affected limb and seek medical attention immediately",
        "Rinse the wound with soap and water, apply an antiseptic ointment, and cover it with a sterile dressing",
        "Rinse the eye with water or saline solution to flush out the object",
        "Administer their prescribed inhaler as directed and seek medical help if the symptoms worsen",
        "Apply a cold compress and immobilize the injured limb with a splint",
        "Offer them sugary food or drink to raise their blood sugar levels",
        "Keep their head and neck immobilized and call for medical help immediately",
        "Apply vinegar or a baking soda paste to the sting area",
        "Administer an epinephrine auto-injector if available and seek immediate medical attention"
    )


    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var showAnswer by remember { mutableStateOf(false) }
    var countdown by remember { mutableIntStateOf(0) }

    val context = LocalContext.current


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(26.dp)
                .background(Color.Blue)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Blue)
                .padding(vertical = 6.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = "AI",
                modifier = Modifier
                    .width(36.dp)
                    .height(36.dp)
                    .clickable {
                        onBackClicked.invoke()
                        Log.e("TAG", "On Back Image Clicked")
                    }
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = context.getString(R.string.app_title),
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Question ${currentQuestionIndex + 1}/24",
                style = MaterialTheme.typography.labelMedium
            )

            Text(
                text = questions[currentQuestionIndex],
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (showAnswer) {
                Text(
                    text = "Answer",
                    style = MaterialTheme.typography.labelMedium
                )

                Text(
                    text = answers[currentQuestionIndex],
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }


            if (!showAnswer) {
                Button(
                    onClick = {
                        if (countdown == 0) {
                            countdown = 3
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red // Set the background color to red
                    )
                ) {
                    if (countdown > 0) {
                        Text("Showing answer in $countdown")
                    } else {
                        Text("Show Answer")
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (currentQuestionIndex > 0) {
                    Button(
                        onClick = {
                            if (currentQuestionIndex > 0) {
                                currentQuestionIndex--
                                showAnswer = false
                                countdown = 0
                            }
                        },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Previous")
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.weight(1f))

                if (currentQuestionIndex < questions.size - 1) {
                    Button(
                        onClick = {
                            if (currentQuestionIndex < questions.size - 1) {
                                currentQuestionIndex++
                                showAnswer = false
                                countdown = 0
                            }
                        },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Next")
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }

        }

    }

//            } })


    // Handle countdown timer
    LaunchedEffect(countdown) {
        if (countdown > 0) {
            delay(1000L)
            if (countdown == 1) {
                showAnswer = true
            }
            countdown--
        }
    }


}