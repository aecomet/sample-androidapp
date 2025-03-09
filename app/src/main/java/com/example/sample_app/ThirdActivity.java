package com.example.sample_app;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;

public class ThirdActivity extends AppCompatActivity {
  private static final String PARENT_FRAGMENT = "PARENT_FRAGMENT";
  private static final String CHILD_FRAGMENT = "CHILD_FRAGMENT";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d("ThirdActivity", "onCreate");

    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_third);

    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    Button returnButton = findViewById(R.id.third_return_button);
    returnButton.setOnClickListener(v -> finish());

    // create fragment
    if (Objects.isNull(savedInstanceState)) {
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.third_frame_layout, new ThirdParentFragment(), PARENT_FRAGMENT)
          .commit();
    }

    // create radio event
    RadioGroup radioGroup = findViewById(R.id.third_fragment_radio_group);
    radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
      Log.d("ThirdActivity", "change radio " + checkedId);

      String newTag = "";
      if (Objects.equals(checkedId, R.id.to_third_parent_fragment)) {
        newTag = PARENT_FRAGMENT;
      } else if (Objects.equals(checkedId, R.id.to_third_child_fragment)) {
        newTag = CHILD_FRAGMENT;
      }

      // get fragment with new tag
      Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(newTag);

      // skip if the fragment is already selected
      if (Objects.nonNull(currentFragment) && currentFragment.isVisible()) {
        Log.d("ThirdActivity", "Same fragment already visible, skipping replace");
        return;
      }


      FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

      if (Objects.equals(checkedId, R.id.to_third_parent_fragment)) {
        fragmentTransaction.replace(R.id.third_frame_layout, new ThirdParentFragment(), PARENT_FRAGMENT);
      } else if (Objects.equals(checkedId, R.id.to_third_child_fragment)) {
        fragmentTransaction.replace(R.id.third_frame_layout, new ThirdChildFragment(), CHILD_FRAGMENT);
      }

      fragmentTransaction.commit();
    });
  }
}
