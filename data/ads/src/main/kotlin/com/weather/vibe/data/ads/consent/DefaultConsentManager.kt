package com.weather.vibe.data.ads.consent

import android.app.Activity
import android.content.Context
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform
import com.weather.vibe.domain.ads.consent.AdConsentState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.suspendCancellableCoroutine
import org.koin.core.annotation.Single
import kotlin.coroutines.resume

@Single(binds = [ConsentManager::class, AdConsentState::class])
internal class DefaultConsentManager(context: Context) : ConsentManager {

  private val consentInformation: ConsentInformation =
    UserMessagingPlatform.getConsentInformation(context)

  private val _canRequestAds =
    MutableStateFlow(consentInformation.canRequestAds())

  override val canRequestAds: Flow<Boolean> =
    _canRequestAds.asStateFlow()

  override suspend fun requestConsentAndShowFormIfNeeded(activity: Activity) {

    requestConsentInfoUpdate(activity)
    showFormIfRequired(activity)

    _canRequestAds.update { consentInformation.canRequestAds() }
  }

  private suspend fun requestConsentInfoUpdate(activity: Activity) {

    val params = ConsentRequestParameters.Builder()
      .setTagForUnderAgeOfConsent(false)
      .build()

    suspendCancellableCoroutine { continuation ->
      consentInformation.requestConsentInfoUpdate(
        activity,
        params,
        { if (continuation.isActive) continuation.resume(Unit) },
        { if (continuation.isActive) continuation.resume(Unit) }
      )
    }
  }

  private suspend fun showFormIfRequired(activity: Activity) {
    suspendCancellableCoroutine { continuation ->
      UserMessagingPlatform.loadAndShowConsentFormIfRequired(activity) {
        if (continuation.isActive) {
          continuation.resume(Unit)
        }
      }
    }
  }
}
