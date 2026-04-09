package com.weather.vibe.domain.weather.model

import java.util.Locale

internal enum class Language(
  val displayName: String,
  val briefExamples: String
) {

  ENGLISH(
    displayName = "English",
    briefExamples = """
      Witty: "Grey skies and drizzle out there, but honestly? A perfect excuse to make coffee and ignore the world for a bit."
      Witty: "The sun is out and it's actually pleasant for once — a walk would not be a waste of your time."
      Formal: "Cloudy and damp conditions through most of the day, with light rain expected in the afternoon."
      Formal: "Sunny skies and mild temperatures provide ideal conditions for outdoor activities."
      Humorous: "Classic grey autumn day out there. All that's missing is a blanket and an existential crisis."
      Humorous: "The sun is being almost suspiciously cooperative. Enjoy it before it remembers where it is."
    """.trimIndent()
  ),

  POLISH(
    displayName = "Polish",
    briefExamples = """
      Witty: "Za oknem szaro i mokro, ale to akurat świetny dzień, żeby zaparzyć sobie kawę, zostać w domu i trochę zwolnić."
      Witty: "Słońce świeci, powietrze rześkie — taki dzień aż prosi się o spacer i zaczerpnięcie odrobiny tlenu."
      Formal: "Pochmurna i wilgotna aura utrzyma się przez większość dnia, z przelotnymi opadami deszczu w godzinach popołudniowych."
      Formal: "Słoneczny dzień z bezchmurnym niebem i łagodną temperaturą sprzyja aktywnościom na świeżym powietrzu."
      Humorous: "Za oknem klasyk polskiej jesieni: szaro, buro i pada. Do kompletu brakuje tylko koca i resztki optymizmu."
      Humorous: "Słońce świeci tak mocno, że aż podejrzanie. Pewnie niedługo się popsuje, więc póki co korzystaj i nie zadawaj pytań."
    """.trimIndent()
  );

  companion object {

    fun fromTag(tag: String): Language =
      when (Locale.forLanguageTag(tag).language) {
        "pl" -> POLISH
        else -> ENGLISH
      }
  }
}
