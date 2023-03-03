package com.albusaidyy.a7minutesworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.albusaidyy.a7minutesworkout.databinding.ActivityExerciseBinding
import com.albusaidyy.a7minutesworkout.databinding.DialogCustomBackConfrimationBinding
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding: ActivityExerciseBinding? = null

    private var restTimer: CountDownTimer? = null
    private var resetProgress = 0
    private var restTimerDuration: Long = 1


    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseTimerDuration: Long = 1

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts: TextToSpeech? = null

    //media player
    private var player: MediaPlayer? = null

    //adapter
    private var exerciseStatusAdapter: ExerciseStatusAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //setSupportActionBar(binding?.toolbarExercise)
        if (supportActionBar != null) {
            //activate back button
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExercise?.setNavigationOnClickListener {
            //go back to previous screen
            customDialogForBackButton()
        }

        exerciseList = Constants.defaultExerciseList()

        //initialize textToSpeech
        tts = TextToSpeech(this, this)



        setUpRestView()
        setUpExerciseStatusRecyclerView()


    }

    override fun onBackPressed() {
        customDialogForBackButton()
        //super.onBackPressed()
    }

    private fun customDialogForBackButton() {
        //custom dialog binding
        val customDialog = Dialog(this)
        val dialogBinding = DialogCustomBackConfrimationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        //touch outside
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.btnYes.setOnClickListener {
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.btnNo.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()

    }

    //set up adapter
    private fun setUpExerciseStatusRecyclerView() {
        binding?.rvExerciseStatus?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        exerciseStatusAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseStatusAdapter
    }

    private fun setUpRestView() {

        try {
            val soundUri =
                Uri.parse("android.resource://com.albusaidyy.a7minutesworkout/" + R.raw.press_start)
            player = MediaPlayer.create(applicationContext, soundUri)
            player?.isLooping = false
            player?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        binding?.flRestView?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvUpcomingLabel?.visibility = View.VISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.flExercise?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE
        if (restTimer != null) {
            restTimer?.cancel()
            resetProgress = 0
        }
        if (currentExercisePosition < exerciseList?.size!! - 1) {
            binding?.tvUpcomingExerciseName?.text =
                exerciseList!![currentExercisePosition + 1].getName()
        } else {
            binding?.tvTitle?.visibility = View.INVISIBLE
            binding?.tvUpcomingExerciseName?.text =
                "Your are Done"
        }
        setRestProgressBar()

    }

    private fun setUpExerciseView() {
        binding?.flRestView?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvUpcomingLabel?.visibility = View.INVISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.flExercise?.visibility = View.VISIBLE
        binding?.ivImage?.visibility = View.VISIBLE
        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        //speak out the text
        speakOut(exerciseList!![currentExercisePosition].getName())
        //set image from the list
        binding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding?.tvExerciseName?.text = exerciseList!![currentExercisePosition].getName()
        setExerciseProgressBar()

    }

    private fun setRestProgressBar() {
        binding?.progressBar?.progress = resetProgress
        restTimer = object : CountDownTimer(restTimerDuration * 10000, 1000) {
            override fun onTick(p0: Long) {
                resetProgress++
                binding?.progressBar?.progress = 10 - resetProgress
                binding?.tvTimer?.text = (10 - resetProgress).toString()
            }

            override fun onFinish() {
                //go to next exercise
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseStatusAdapter!!.notifyDataSetChanged()
                setUpExerciseView()

            }

        }.start()


    }

    private fun setExerciseProgressBar() {

        binding?.progressBarExercise?.progress = exerciseProgress
        exerciseTimer = object : CountDownTimer(exerciseTimerDuration * 30000, 1000) {
            override fun onTick(p0: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = 30 - exerciseProgress
                binding?.tvTimerExercise?.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {

                if (currentExercisePosition < exerciseList?.size!! - 1) {
                    //set items when
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseStatusAdapter!!.notifyDataSetChanged()
                    setUpRestView()
                } else {
                    finish()
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                }

            }

        }.start()

    }

    override fun onDestroy() {
        super.onDestroy()
        if (restTimer != null) {
            restTimer?.cancel()
            resetProgress = 0
        }
        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        if (tts != null) {
            tts?.stop()
            tts?.shutdown()
        }

        if (player != null) {
            player!!.stop()
        }

        binding = null

    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            //set language to speak
            val result = tts?.setLanguage(Locale.ENGLISH)
            //check for errors and log
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported")
            }

        } else {
            Log.e("TTS", "Initialization failed!")
        }
    }

    //method to speak
    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }
}