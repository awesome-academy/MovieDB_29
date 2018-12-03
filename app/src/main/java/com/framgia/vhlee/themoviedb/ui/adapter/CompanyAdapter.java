package com.framgia.vhlee.themoviedb.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.framgia.vhlee.themoviedb.R;
import com.framgia.vhlee.themoviedb.data.model.Company;
import com.framgia.vhlee.themoviedb.databinding.ItemCompanyBinding;

import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.ViewHolder> {
    private List<Company> mCompanies;

    public CompanyAdapter(List<Company> companies) {
        mCompanies = companies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCompanyBinding itemCompanyBinding =
                DataBindingUtil.inflate(inflater, R.layout.item_company, parent, false);
        return new ViewHolder(itemCompanyBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mCompanies.get(position));
    }

    @Override
    public int getItemCount() {
        return mCompanies != null ? mCompanies.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemCompanyBinding mItemCompanyBinding;

        public ViewHolder(ItemCompanyBinding itemCompanyBinding) {
            super(itemCompanyBinding.getRoot());
            mItemCompanyBinding = itemCompanyBinding;
        }

        public void bindData(Company company) {
            mItemCompanyBinding.setCompany(company);
        }
    }
}
