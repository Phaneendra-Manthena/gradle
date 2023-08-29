package com.bhargo.user.controls.variables;

//VoiceRecording,VideoRecording,AudioPlayer,VideoPlayer,Signature,Image,Camera,FileBrowsing
public interface PathVariables {
    //voiceRecordPath,visible,enable,clear
    String getPath();

    void setPath(String path);

    boolean getVisibility();

    void setVisibility(boolean visibility);
    boolean isEnabled();

    void setEnabled(boolean enabled);

    void clean();

    void showMessageBelowControl(String msg);
}
