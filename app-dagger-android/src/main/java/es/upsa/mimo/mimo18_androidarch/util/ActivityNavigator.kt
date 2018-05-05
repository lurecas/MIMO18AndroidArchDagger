package es.upsa.mimo.mimo18_androidarch.util

import android.content.Context
import android.content.Intent
import es.upsa.mimo.mimo18_androidarch.detail.CharacterDetailActivity

interface ActivityNavigator {
    fun openCharacterActivity(charId: String)
}

class ActivityNavigatorImpl(
        private val context: Context
) : ActivityNavigator {

    override fun openCharacterActivity(charId: String) {

        with(Intent(context, CharacterDetailActivity::class.java)) {
            putExtra(CharacterDetailActivity.BUNDLE_CHARACTER_ID, charId)
            context.startActivity(this)
        }

    }

}