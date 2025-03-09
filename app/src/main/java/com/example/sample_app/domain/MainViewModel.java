package com.example.sample_app.domain;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class MainViewModel extends ViewModel {
  private final HashMap<Integer, MutableLiveData<Integer>> buttonClickEvents = new HashMap<>();
  private final MutableLiveData<List<AppCompatImageButton>> fabButtonEvent = new MutableLiveData<>();
  private Boolean isFabOpen = false;

  public MutableLiveData<Integer> getButtonClickEvent(Integer buttonId) {
    if (!buttonClickEvents.containsKey(buttonId)) {
      buttonClickEvents.put(buttonId, new MutableLiveData<>());
    }
    return buttonClickEvents.get(buttonId);
  }

  public void onButtonClick(Integer buttonId) {
    if (!buttonClickEvents.containsKey(buttonId)) {
      buttonClickEvents.put(buttonId, new MutableLiveData<>());
    }
    try {
      Objects.requireNonNull(buttonClickEvents.get(buttonId)).setValue(buttonId);
    } catch (NullPointerException e) {
      Logger.getLogger("").info("");
    }
  }

  public MutableLiveData<List<AppCompatImageButton>> getFabButtonEvent() {
    return fabButtonEvent;
  }

  public void onFabButtonClick(List<AppCompatImageButton> fabButtons) {
    fabButtonEvent.setValue(fabButtons);
  }

  public void setIsFabOpen(Boolean isFabOpen) {
    this.isFabOpen = isFabOpen;
  }

  public Boolean getFabOpen() {
    return isFabOpen;
  }
}
