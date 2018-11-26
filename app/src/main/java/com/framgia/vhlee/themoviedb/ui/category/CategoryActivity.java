package com.framgia.vhlee.themoviedb.ui.category;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.framgia.vhlee.themoviedb.R;
import com.framgia.vhlee.themoviedb.databinding.ActivityCategoryBinding;

public class CategoryActivity extends AppCompatActivity {
    private static final String EXTRA_ARGS = "com.framgia.vhlee.themoviedb.extras.EXTRA_ARGS";
    private static final String BUNDLE_TYPE = "BUNDLE_TYPE";
    private static final String BUNDLE_CODE = "BUNDLE_CODE";
    private CategoryViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCategoryBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_category);
        initViewModel();
        binding.setCategoryVM(mViewModel);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewModel.destroy();
    }

    public static Intent getCategoryIntent(Context context, Bundle bundle) {
        Intent intent = new Intent(context, CategoryActivity.class);
        intent.putExtra(EXTRA_ARGS, bundle);
        return intent;
    }

    private void initViewModel() {
        Bundle bundle = getIntent().getBundleExtra(EXTRA_ARGS);
        mViewModel = new CategoryViewModel(bundle.getString(BUNDLE_TYPE));
        mViewModel.loaMovies(bundle.getBoolean(BUNDLE_CODE));
    }
}
