package com.example.pokedex.ui

import com.example.core.base.BaseFragment
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentListPokemonBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListPokemonFragment : BaseFragment<FragmentListPokemonBinding>(){

    override fun layoutId() = R.layout.fragment_list_pokemon

}