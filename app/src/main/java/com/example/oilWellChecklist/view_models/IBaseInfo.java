package com.example.oilWellChecklist.view_models;

import android.media.Image;
import java.util.List;

public interface IBaseInfo {
    Image _image = null;
    String _description = "";
    List<Checkpoint> _checkpoints = null;
}
