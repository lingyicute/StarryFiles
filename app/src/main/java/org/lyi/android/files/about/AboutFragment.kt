/*
 * Copyright (c) 2018 Hai Zhang <dreaming.in.code.zh@gmail.com>
 * All Rights Reserved.
 */

package org.lyi.android.files.about

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import org.lyi.android.files.databinding.AboutFragmentBinding
import org.lyi.android.files.ui.LicensesDialogFragment
import org.lyi.android.files.util.createViewIntent
import org.lyi.android.files.util.startActivitySafe

class AboutFragment : Fragment() {
    private lateinit var binding: AboutFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        AboutFragmentBinding.inflate(inflater, container, false)
            .also { binding = it }
            .root

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.gitHubLayout.setOnClickListener { startActivitySafe(GITHUB_URI.createViewIntent()) }
        binding.licensesLayout.setOnClickListener { LicensesDialogFragment.show(this) }
        binding.authorNameLayout.setOnClickListener {
            startActivitySafe(AUTHOR_RESUME_URI.createViewIntent())
        }
        binding.authorGitHubLayout.setOnClickListener {
            startActivitySafe(AUTHOR_GITHUB_URI.createViewIntent())
        }
    }

    companion object {
        private val GITHUB_URI = Uri.parse("https://github.com/lingyicute/StarryFiles")
        private val PRIVACY_POLICY_URI =
            Uri.parse("https://github.com/lingyicute/StarryFiles/blob/master/PRIVACY.md")
        private val AUTHOR_RESUME_URI = Uri.parse("https://92li.us.kg")
        private val AUTHOR_GITHUB_URI = Uri.parse("https://github.com/lingyicute")
    }
}
