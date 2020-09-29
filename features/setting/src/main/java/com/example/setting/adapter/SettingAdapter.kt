package com.example.setting.adapter

import android.content.Context
import com.example.core.adapter.BaseViewAdapter
import com.example.setting.R
import com.example.setting.model.SettingItem

class SettingAdapter constructor(
    listSetting: List<SettingItem>,
    context: Context
) : BaseViewAdapter<SettingItem>(context, listSetting) {

    override fun layoutId() = R.layout.adapter_item_title_setting

}