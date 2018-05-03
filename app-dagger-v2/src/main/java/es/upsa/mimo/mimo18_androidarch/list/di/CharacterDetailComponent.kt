package es.upsa.mimo.mimo18_androidarch.list.di

import dagger.Subcomponent
import es.upsa.mimo.mimo18_androidarch.detail.CharacterDetailActivity
import es.upsa.mimo.mimo18_androidarch.di.scopes.DetailScope

@Subcomponent
@DetailScope
interface CharacterDetailComponent {
    fun inject(activity: CharacterDetailActivity)
}