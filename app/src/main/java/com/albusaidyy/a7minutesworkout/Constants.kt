package com.albusaidyy.a7minutesworkout

object Constants {
    //return an arrayList of exerciseModel
    fun defaultExerciseList(): ArrayList<ExerciseModel> {
        //initialize the arrayList
        val exerciseList = ArrayList<ExerciseModel>()
        //sample arrayList
        val jumpingJacks = ExerciseModel(1, "jumping jacks", R.drawable.jumping_jacks, false, false)
        exerciseList.add(jumpingJacks)

        val pushUp = ExerciseModel(2, "Push Up", R.drawable.push_ups, false, false)
        exerciseList.add(pushUp)

        val abdominalCrunch =
            ExerciseModel(3, "Abdominal Crunch", R.drawable.abdominal_crunch, false, false)
        exerciseList.add(abdominalCrunch)

        return exerciseList

    }
}