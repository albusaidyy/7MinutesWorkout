package com.albusaidyy.a7minutesworkout

object Constants {
    //return an arrayList of exerciseModel
    fun defaultExerciseList(): ArrayList<ExerciseModel> {
        //initialize the arrayList
        val exerciseList = ArrayList<ExerciseModel>()
        //sample arrayList
        val jumpingJacks = ExerciseModel(1, "jumping jacks", R.drawable.seven_image, false, false)
        exerciseList.add(jumpingJacks)

        val walSit = ExerciseModel(2, "Wall Sit", R.drawable.seven_image, false, false)
        exerciseList.add(walSit)

        val pushUp = ExerciseModel(3, "Push Up", R.drawable.seven_image, false, false)
        exerciseList.add(pushUp)

        val abdominalCrunch =
            ExerciseModel(4, "Abdominal Crunch", R.drawable.seven_image, false, false)
        exerciseList.add(abdominalCrunch)

        val stepUpOnChair = ExerciseModel(5, "Step Up On Chair", R.drawable.seven_image, false, false)
        exerciseList.add(stepUpOnChair)

        return exerciseList

    }
}