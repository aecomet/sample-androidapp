package com.example.sample_app;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.sample_app.domain.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
  private MainViewModel viewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);

    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    // set View Model
    viewModel = new ViewModelProvider(this).get(MainViewModel.class);

    // create button event
    List<Button> buttons = new ArrayList<>() {
      {
        add(findViewById(R.id.first_button));
        add(findViewById(R.id.second_button));
      }
    };

    buttons.forEach(this::createButtonEvent);

    // create fab button
    FloatingActionButton fabMain = findViewById(R.id.floating_button);
    fabMain.setOnClickListener(v -> toggleFabMenu());

    List<AppCompatImageButton> fabButtons = new ArrayList<>() {
      {
        add(findViewById(R.id.fab_sub1));
        add(findViewById(R.id.fab_sub2));
        add(findViewById(R.id.fab_sub3));
      }
    };

    viewModel.onFabButtonClick(fabButtons);
  }

  /**
   * Create Button Event
   *
   * @param button Button Component
   */
  private void createButtonEvent(Button button) {
    button.setOnClickListener(v -> viewModel.onButtonClick(button.getId()));
    viewModel.getButtonClickEvent(button.getId()).observe(this, id -> {
      if (Objects.nonNull(id)) {
        Toast.makeText(this, String.format("Button %s Clicked!", id), Toast.LENGTH_SHORT).show();
      }
    });
  }

  /**
   * Toggle Sub floating action buttons
   */
  private void toggleFabMenu() {
    if (viewModel.getFabOpen()) {
      // close sub menu
      viewModel.getFabButtonEvent().observe(this, fabButtons -> {
        fabButtons.forEach(fab -> {
          Animation animation = AnimationUtils.loadAnimation(this, R.anim.fab_close);
          fab.startAnimation(animation);
          fab.setVisibility(View.GONE);
        });
      });

      viewModel.setIsFabOpen(false);
    } else {
      // open sub menu
      viewModel.getFabButtonEvent().observe(this, fabButtons -> {
        fabButtons.forEach(fab -> {
          Animation animation = AnimationUtils.loadAnimation(this, R.anim.fab_open);
          fab.startAnimation(animation);
          fab.setVisibility(View.VISIBLE);
        });
      });

      viewModel.setIsFabOpen(true);
    }
  }

}
