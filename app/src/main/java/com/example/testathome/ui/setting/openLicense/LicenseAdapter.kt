package com.example.testathome.ui.setting.openLicense

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testathome.databinding.LicenseItemBinding

class LicenseAdapter(_items:ArrayList<OpenLicenseFragment.LicenseData>):RecyclerView.Adapter<LicenseAdapter.LicenseViewHolder>() {
    var itemList=_items
    class LicenseViewHolder(_binding:LicenseItemBinding):RecyclerView.ViewHolder(_binding.root) {
        val binding=_binding
        fun bind(item: OpenLicenseFragment.LicenseData){
            binding.licenseData=item
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LicenseViewHolder {
        val binding = LicenseItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LicenseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LicenseViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}