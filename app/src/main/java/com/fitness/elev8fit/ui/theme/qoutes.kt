package com.fitness.elev8fit.ui.theme

object Quotes {
    val quoteList = listOf(
        "Push yourself because no one else is going to do it for you.",
        "The only bad workout is the one that didn’t happen.",
        "Exercise is a celebration of what your body can do, not a punishment for what you ate.",
        "Don’t stop when you’re tired. Stop when you’re done.",
        "It’s not about being the best. It’s about being better than you were yesterday.",
        "The body achieves what the mind believes.",
        "A little progress each day adds up to big results.",
        "Success starts with self-discipline.",
        "Sweat is fat crying.",
        "When you feel like quitting, think about why you started.",
        "Don’t wish for a good body, work for it.",
        "Hard work beats talent when talent doesn’t work hard.",
        "You don’t have to be great to start, but you have to start to be great.",
        "Strength does not come from what you can do. It comes from overcoming the things you once thought you couldn’t.",
        "It’s going to be a journey. It’s not a sprint to get in shape.",
        "There are no shortcuts. Work hard, stay consistent, and achieve greatness.",
        "The pain you feel today will be the strength you feel tomorrow.",
        "Your only limit is you.",
        "The harder you work for something, the greater you’ll feel when you achieve it.",
        "You don’t get the ass you want by sitting on it.",
        "Take care of your body, it’s the only place you have to live.",
        "Discipline is the bridge between goals and accomplishment.",
        "Make your body the sexiest outfit you own.",
        "Work hard in silence, let success be your noise.",
        "You don’t have to go fast, you just have to go.",
        "Don’t count the days, make the days count.",
        "The only way to finish is to start.",
        "A one-hour workout is 4% of your day. No excuses.",
        "Believe in yourself and all that you are.",
        "Fitness is not about being better than someone else, it’s about being better than you used to be."
    )

    fun getRandomQuote(): String {
        return quoteList.random() // Returns a random quote from the list
    }
}