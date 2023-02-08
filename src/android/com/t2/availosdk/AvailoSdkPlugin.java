package com.t2.availosdk;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import sa.t2.availosdk.engine.*;
import sa.t2.availosdk.model.apiResult.UiResult;
import sa.t2.availosdk.model.attendance.AttendanceResponse;
import sa.t2.availosdk.model.attendance.AttendanceUiResult;
import sa.t2.availosdk.model.attendance.RandomAttendanceValidationResponse;
import sa.t2.availosdk.model.profile.ProfileResponse;
import sa.t2.availosdk.model.profile.VoiceEnableInfo;
import sa.t2.availosdk.model.registration.FilesCountData;
import sa.t2.availosdk.model.registration.RandomText;
import sa.t2.availosdk.model.registration.RegistrationUiResult;
import sa.t2.availosdk.model.scan.GateScanResult;
import sa.t2.availosdk.model.user.LoginResponse;
import sa.t2.availosdk.model.user.UserTransaction;
import sa.t2.availosdk.utils.TransactionType;

/**
 * This class echoes a string called from JavaScript.
 */
public class AvailoSdkPlugin extends CordovaPlugin {

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

    if (action.equals("initSDK")) {
      String url = args.getJSONArray(0).getString(0);
      String clientKey = args.getJSONArray(0).getString(1);
      initialize(url, clientKey, callbackContext);
      return true;
    } else if (action.equals("loginByCredentials")) {
      String user = args.getJSONArray(0).getString(0);
      String password = args.getJSONArray(0).getString(1);
      login(user, password, callbackContext);
      return true;
    } else if (action.equals("getLastTransaction")) {
      getLastTransaction(callbackContext);
      return true;
    } else if (action.equals("startScan")) {
      startScan(callbackContext);
      return true;
    } else if (action.equals("stopScan")) {
      stopScan(callbackContext);
      return true;
    } else if (action.equals("attendByFace")) {
      String imgBase64 = args.getJSONArray(0).getString(0);
      String transactionType = args.getJSONArray(0).getString(1);
      attendByFace(imgBase64, transactionType, callbackContext);
      return true;
    } else if (action.equals("attendByVoice")) {
      String filePath = args.getJSONArray(0).getString(0);
      String voiceId = args.getJSONArray(0).getString(1);
      String transactionType = args.getJSONArray(0).getString(2);
      attendByVoice(filePath, voiceId, transactionType, callbackContext);
      return true;
    } else if (action.equals("getRandomTextForAttendance")) {
      getRandomTextForAttendance(callbackContext);
      return true;
    } else if (action.equals("registerImage")) {
      String imgBase64 = args.getJSONArray(0).getString(0);
      registerImage(imgBase64, callbackContext);
      return true;
    } else if (action.equals("registerVoiceFile")) {
      String filePath = args.getJSONArray(0).getString(0);
      String voiceId = args.getJSONArray(0).getString(1);
      registerVoiceFile(filePath, voiceId, callbackContext);
      return true;
    } else if (action.equals("getRandomTextForSignup")) {
      getRandomTextForSignup(callbackContext);
      return true;
    } else if (action.equals("changeUserStatus")) {
      changeUserStatus(callbackContext);
      return true;
    } else if (action.equals("getRequiredFilesCount")) {
      getRequiredFilesCount(callbackContext);
      return true;
    } else if (action.equals("addNewFaceFile")) {
      String imgBase64 = args.getJSONArray(0).getString(0);
      addNewFaceFile(imgBase64, callbackContext);
      return true;
    } else if (action.equals("addNewVoiceFile")) {
      String filePath = args.getJSONArray(0).getString(0);
      String voiceId = args.getJSONArray(0).getString(1);
      addNewVoiceFile(filePath, voiceId, callbackContext);
      return true;
    } else if (action.equals("getVoiceEnableInfo")) {
      getVoiceEnableInfo(callbackContext);
      return true;
    } else if (action.equals("validateRandomAttendance")) {
      validateRandomAttendance(callbackContext);
      return true;
    }else if (action.equals("getUserProfile")) {
      getUserProfile(callbackContext);
      return true;
    }
    return false;
  }

  private void initialize(String url, String clientKey, CallbackContext callbackContext) {
    try {
      AvailoSDKEngine.INSTANCE.initialize(
        cordova.getActivity().getApplication(),
        url,
        clientKey);
      callbackContext.sendPluginResult(
        new PluginResult(
          PluginResult.Status.OK,
          true));
    } catch (Exception e) {
      e.printStackTrace();
      callbackContext.sendPluginResult(
        new PluginResult(
          PluginResult.Status.ERROR,
          e.getMessage()));
    }
  }

  private void login(String user, String password, CallbackContext callbackContext) {
    AvailoSDKEngine.Authentication.INSTANCE.loginByCredentials(user, password,
      new Continuation<UiResult<LoginResponse>>() {
        @NonNull
        @Override
        public CoroutineContext getContext() {
          return EmptyCoroutineContext.INSTANCE;
        }

        @Override
        public void resumeWith(@NonNull Object result) {
          UiResult<LoginResponse> loginResult = ((UiResult<LoginResponse>) result);
          String error = loginResult.getErrorMessage();
          if (error == null || error.isEmpty()) {
            callbackContext.sendPluginResult(
              new PluginResult(
                PluginResult.Status.OK,
                loginResult.getData().toJson()));
          } else {
            callbackContext.sendPluginResult(
              new PluginResult(
                PluginResult.Status.ERROR,
                error));
          }
        }
      });
  }

  private void getLastTransaction(CallbackContext callbackContext) {
    AvailoSDKEngine.Profile.INSTANCE.getLastTransaction(
      new Continuation<UiResult<UserTransaction>>() {
        @NonNull
        @Override
        public CoroutineContext getContext() {
          return EmptyCoroutineContext.INSTANCE;
        }

        @Override
        public void resumeWith(@NonNull Object result) {
          UiResult<UserTransaction> lastTransactions = ((UiResult<UserTransaction>) result);
          String error = lastTransactions.getErrorMessage();
          String s ;
          if (lastTransactions.getData() != null) {
            s = lastTransactions.getData().toJson();
          }else {
            s = null;
          }
          Log.d("moesTransaction","lastTransactions"+lastTransactions.getData());
          if (error == null || error.isEmpty()) {
            callbackContext.sendPluginResult(
              new PluginResult(
                PluginResult.Status.OK,
                s));
          } else {
            callbackContext.sendPluginResult(
              new PluginResult(
                PluginResult.Status.ERROR,
                error));
          }
        }
      }
    );
  }

  private void stopScan(CallbackContext callbackContext) {
    AvailoSDKEngine.GatesScanning.INSTANCE.stopScan();
    callbackContext.sendPluginResult(
      new PluginResult(
        PluginResult.Status.OK,
        "stoped Successfuly"));
  }

  private void startScan(CallbackContext callbackContext) {
    cordova.getActivity().runOnUiThread(
      new Runnable() {
        @Override
        public void run() {
          AvailoSDKEngine.GatesScanning.INSTANCE.startScan().observe(
            cordova.getActivity(), // your lifecycle owner (activity for example)
            new Observer<GateScanResult>() {
              @Override
              public void onChanged(GateScanResult gateScanResult) {
                JSONObject s = new JSONObject();
                try {
                  s.put("canAttend", gateScanResult.getCanAttend());
                  s.put("ArabicName", gateScanResult.getGateArabicName());
                  s.put("EnglishName", gateScanResult.getGateEnglishName());
                  s.put("GateId", gateScanResult.getGateId());
                  s.put("Message", gateScanResult.getMessage());

                } catch (Exception e) {

                }
                PluginResult result = new PluginResult(PluginResult.Status.OK, s);
                result.setKeepCallback(true);
                callbackContext.sendPluginResult(result);
              }
            });
        }
      });

  }

  private void attendByFace(String imageBase64, String transactionTypeCode, CallbackContext callbackContext) {
    Log.d("moes", "attendByFace1");
    TransactionType transactionType = null;
    if(transactionTypeCode.equals("2")){
      transactionType =TransactionType.CHECKOUT;
    }else if(transactionTypeCode.equals("1")){
      transactionType = TransactionType.CHECK_IN;
    }else if(transactionTypeCode.equals("4")){
      transactionType = TransactionType.CHECK_IN;
    }

    Log.d("moes", "attendByFace2");

    AvailoSDKEngine.Attendance.INSTANCE.attendByFace(imageBase64, transactionType,
      new Continuation<UiResult<AttendanceUiResult>>() {
        @NonNull
        @Override
        public CoroutineContext getContext() {
          Log.d("moes", "attendByFace3");
          return EmptyCoroutineContext.INSTANCE;
        }

        @Override
        public void resumeWith(@NonNull Object result) {
          Log.d("moes", "attendByFace4");

          UiResult<AttendanceUiResult> attendanceResult = ((UiResult<AttendanceUiResult>) result);
          Log.d("moes", "attendByFace5" + attendanceResult);

          String error = attendanceResult.getErrorMessage();
          Log.d("moes", "attendByFace6" + error);
          JSONObject s = new JSONObject();
          try {
            if (attendanceResult.getData().getError() != null) {
              s.put("error", attendanceResult.getData().getError().toString());
            }
            s.put("isSuccess", attendanceResult.getData().isSuccess());
            s.put("message", attendanceResult.getData().getMessage());
            s.put("transaction", attendanceResult.getData().getTransaction().toJson());

          } catch (Exception e) {
            Log.d("moesException ","Exception is"+ e);
          }
          Log.d("moes", "attendByFace7" + s);

          if (error == null || error.isEmpty()) {
            callbackContext.sendPluginResult(
              new PluginResult(
                PluginResult.Status.OK,
                s));
          } else {
            callbackContext.sendPluginResult(
              new PluginResult(
                PluginResult.Status.ERROR,
                error));
          }
        }
      });
  }

  private void attendByVoice(String voiceFile,
                             String voiceId, String transactionTypeCode, CallbackContext callbackContext) {
    TransactionType transactionType = null;
    if(transactionTypeCode.equals("2")){
      transactionType =TransactionType.CHECKOUT;
    }else if(transactionTypeCode.equals("1")){
      transactionType = TransactionType.CHECK_IN;
    }else if(transactionTypeCode.equals("4")){
      transactionType = TransactionType.CHECK_IN;
    }
    Log.d("moes 102", "transactionType is :"+transactionType);

    Log.d("moes 102", transactionTypeCode);
    Log.d("moes 120", voiceFile);
    Log.d("moes 1200", voiceId);
    File voice = new File(voiceFile.replace("file:///", ""));
    Log.d("moes 1200", String.valueOf(voice.exists()));


    AvailoSDKEngine.Attendance.INSTANCE.attendByVoice(voice, voiceId, transactionType,
      new Continuation<UiResult<AttendanceUiResult>>() {
        @NonNull
        @Override
        public CoroutineContext getContext() {
          return EmptyCoroutineContext.INSTANCE;
        }

        @Override
        public void resumeWith(@NonNull Object result) {
          UiResult<AttendanceUiResult> attendanceResult = ((UiResult<AttendanceUiResult>) result);
          Log.d("moes", "AttendanceUiResult Voice" + attendanceResult);
          JSONObject s = new JSONObject();
          try {
            if (attendanceResult.getData().getError() != null) {
              s.put("error", attendanceResult.getData().getError().toString());
            }
            s.put("isSuccess", attendanceResult.getData().isSuccess());
            s.put("message", attendanceResult.getData().getMessage());
            s.put("transaction", attendanceResult.getData().getTransaction().toJson());
            Log.d("moes attend voice","AttendanceUiResult"+s);
          } catch (Exception e) {
            Log.d("moesException ","Exception is"+ e);

          }
          String error = attendanceResult.getErrorMessage();
          if (error == null || error.isEmpty()) {
            callbackContext.sendPluginResult(
              new PluginResult(
                PluginResult.Status.OK,
                s));
          } else {
            callbackContext.sendPluginResult(
              new PluginResult(
                PluginResult.Status.ERROR,
                error));
          }
        }
      });
  }

  private void getRandomTextForAttendance(CallbackContext callbackContext) {
    AvailoSDKEngine.Attendance.INSTANCE
      .getRandomTextForAttendance(
        new Continuation<UiResult<List<RandomText>>>() {
          @NonNull
          @Override
          public CoroutineContext getContext() {
            return EmptyCoroutineContext.INSTANCE;
          }

          @Override
          public void resumeWith(@NonNull Object result) {
            UiResult<List<RandomText>> TextForAttendance = ((UiResult<List<RandomText>>) result);
            JSONArray txt = new JSONArray();
            try {
              // now just loop the json Array
              for (int i = 0; i < TextForAttendance.getData().size(); ++i) {
                RandomText rec = TextForAttendance.getData().get(i);
                JSONObject s = new JSONObject();
                Log.d("Moes 500", rec.toString());

                s.put("AccountId", rec.getAccountId());
                s.put("ID", rec.getId());
                s.put("VoiceTxt", rec.getVoiceText());
                s.put("LanguageId", rec.getLanguageId());
                txt.put(i, s);
                Log.d("Moes 600", txt.toString());

              }
            } catch (JSONException e) {
              e.printStackTrace();
            }
            String error = TextForAttendance.getErrorMessage();
            if (error == null || error.isEmpty()) {
              callbackContext.sendPluginResult(
                new PluginResult(
                  PluginResult.Status.OK,
                  txt));
            } else {
              callbackContext.sendPluginResult(
                new PluginResult(
                  PluginResult.Status.ERROR,
                  error));
            }
          }
        });

  }

  private void getRandomTextForSignup(CallbackContext callbackContext) {
    AvailoSDKEngine.Registration.INSTANCE
      .getRandomTextForSignup(
        new Continuation<UiResult<List<RandomText>>>() {
          @NonNull
          @Override
          public CoroutineContext getContext() {
            return EmptyCoroutineContext.INSTANCE;
          }

          @Override
          public void resumeWith(@NonNull Object result) {
            UiResult<List<RandomText>> TextForSignup = ((UiResult<List<RandomText>>) result);
            JSONArray txt = new JSONArray();
            try {
              // now just loop the json Array
              for (int i = 0; i < TextForSignup.getData().size(); ++i) {
                RandomText rec = TextForSignup.getData().get(i);
                JSONObject s = new JSONObject();
                Log.d("Moes 500", rec.toString());

                s.put("AccountId", rec.getAccountId());
                s.put("ID", rec.getId());
                s.put("VoiceTxt", rec.getVoiceText());
                s.put("LanguageId", rec.getLanguageId());
                txt.put(i, s);
                Log.d("Moes 600", txt.toString());

              }
            } catch (JSONException e) {
              e.printStackTrace();
            }
            String error = TextForSignup.getErrorMessage();
            if (error == null || error.isEmpty()) {
              callbackContext.sendPluginResult(
                new PluginResult(
                  PluginResult.Status.OK,
                  txt));
            } else {
              callbackContext.sendPluginResult(
                new PluginResult(
                  PluginResult.Status.ERROR,
                  error));
            }
          }
        });
  }

  private void registerImage(String imageBase64, CallbackContext callbackContext) {

    AvailoSDKEngine.Registration.INSTANCE.registerImage(imageBase64,
      new Continuation<UiResult<RegistrationUiResult>>() {
        @NonNull
        @Override
        public CoroutineContext getContext() {
          return EmptyCoroutineContext.INSTANCE;
        }

        @Override
        public void resumeWith(@NonNull Object result) {
          UiResult<RegistrationUiResult> registerResult = ((UiResult<RegistrationUiResult>) result);
          JSONObject s = new JSONObject();
          try {
            if (registerResult.getData().getError() != null) {
              s.put("error", registerResult.getData().getError().toString());
            }
            s.put("isSuccess", registerResult.getData().isSuccess());
            s.put("message", registerResult.getData().getMessage());
          } catch (Exception e) {
            Log.d("moesException ","Exception is"+ e);
          }
          Log.d("moes", "attendByFace7" + s);


          String error = registerResult.getErrorMessage();
          if (error == null || error.isEmpty()) {
            callbackContext.sendPluginResult(
              new PluginResult(
                PluginResult.Status.OK,
                s));
          } else {
            callbackContext.sendPluginResult(
              new PluginResult(
                PluginResult.Status.ERROR,
                error));
          }
        }
      });
  }

  private void registerVoiceFile(String voiceFile, String voiceId, CallbackContext callbackContext) {
    File voice = new File(voiceFile.replace("file:///", ""));

    AvailoSDKEngine.Registration.INSTANCE.registerVoiceFile(voice, voiceId,
        new Continuation<UiResult<RegistrationUiResult>>() {
          @NonNull
          @Override
          public CoroutineContext getContext() {
            return EmptyCoroutineContext.INSTANCE;
          }
          @Override
          public void resumeWith(@NonNull Object result) {
            UiResult<RegistrationUiResult> registerVoiceResult = ((UiResult<RegistrationUiResult>) result);
            JSONObject s = new JSONObject();
            try {
              if (registerVoiceResult.getData().getError() != null) {
                s.put("error", registerVoiceResult.getData().getError().toString());
              }
              s.put("isSuccess", registerVoiceResult.getData().isSuccess());
              s.put("message", registerVoiceResult.getData().getMessage());
            } catch (Exception e) {
              Log.d("moesException ","Exception is"+ e);
            }
            String error = registerVoiceResult.getErrorMessage();
            if (error == null || error.isEmpty()) {
              callbackContext.sendPluginResult(
                new PluginResult(
                  PluginResult.Status.OK,
                  s));
            } else {
              callbackContext.sendPluginResult(
                new PluginResult(
                  PluginResult.Status.ERROR,
                  error));
            }
          }
        });
  }

  private void getRequiredFilesCount(CallbackContext callbackContext) {
    AvailoSDKEngine.Registration.INSTANCE
      .getRequiredFilesCount(
        new Continuation<UiResult<FilesCountData>>() {
          @NonNull
          @Override
          public CoroutineContext getContext() {
            return EmptyCoroutineContext.INSTANCE;
          }

          @Override
          public void resumeWith(@NonNull Object result) {
            UiResult<FilesCountData> filesCount = ((UiResult<FilesCountData>) result);
            JSONObject s = new JSONObject();
            try {
              s.put("FacesFilesCount", filesCount.getData().getFaceFilesCount());
              s.put("VoicesFilesCount ", filesCount.getData().getVoiceFilesCount());
            } catch (JSONException e) {
              e.printStackTrace();
            }
            String error = filesCount.getErrorMessage();
            if (error == null || error.isEmpty()) {
              callbackContext.sendPluginResult(
                new PluginResult(
                  PluginResult.Status.OK,
                  s));
            } else {
              callbackContext.sendPluginResult(
                new PluginResult(
                  PluginResult.Status.ERROR,
                  error));
            }
          }
        });
  }

  private void changeUserStatus(CallbackContext callbackContext) {
    AvailoSDKEngine.Registration.INSTANCE
      .changeUserStatus(
        new Continuation<UiResult>() {
          @NonNull
          @Override
          public CoroutineContext getContext() {
            return EmptyCoroutineContext.INSTANCE;
          }

          @Override
          public void resumeWith(@NonNull Object result) {
            UiResult UserStatus = ((UiResult) result);
            String error = UserStatus.getErrorMessage();
            if (error == null || error.isEmpty()) {
              callbackContext.sendPluginResult(
                new PluginResult(
                  PluginResult.Status.OK,
                  UserStatus.toString()));
            } else {
              callbackContext.sendPluginResult(
                new PluginResult(
                  PluginResult.Status.ERROR,
                  error));
            }
          }
        });
  }

  private void addNewFaceFile(String imageBase64, CallbackContext callbackContext) {

    AvailoSDKEngine.Profile.INSTANCE.addNewFaceFile(imageBase64,
      new Continuation<UiResult<RegistrationUiResult>>() {
        @NonNull
        @Override
        public CoroutineContext getContext() {
          return EmptyCoroutineContext.INSTANCE;
        }

        @Override
        public void resumeWith(@NonNull Object result) {
          UiResult<RegistrationUiResult> registerResult = ((UiResult<RegistrationUiResult>) result);
          JSONObject s = new JSONObject();
          try {
            if (registerResult.getData().getError() != null) {
              s.put("error", registerResult.getData().getError().toString());
            }
            s.put("isSuccess", registerResult.getData().isSuccess());
            s.put("message", registerResult.getData().getMessage());
          } catch (Exception e) {
            Log.d("moesException ","Exception is"+ e);
          }

          String error = registerResult.getErrorMessage();
          if (error == null || error.isEmpty()) {
            callbackContext.sendPluginResult(
              new PluginResult(
                PluginResult.Status.OK,
                s));
          } else {
            callbackContext.sendPluginResult(
              new PluginResult(
                PluginResult.Status.ERROR,
                error));
          }
        }
      });
  }

  private void addNewVoiceFile(String voiceFile, String voiceId, CallbackContext callbackContext) {
    File voice = new File(voiceFile.replace("file:///", ""));

    AvailoSDKEngine.Registration.INSTANCE
      .registerVoiceFile(voice, voiceId,
        new Continuation<UiResult<RegistrationUiResult>>() {
          @NonNull
          @Override
          public CoroutineContext getContext() {
            return EmptyCoroutineContext.INSTANCE;
          }

          @Override
          public void resumeWith(@NonNull Object result) {
            UiResult<RegistrationUiResult> registerVoiceResult = ((UiResult<RegistrationUiResult>) result);
            JSONObject s = new JSONObject();
            try {
              if (registerVoiceResult.getData().getError() != null) {
                s.put("error", registerVoiceResult.getData().getError().toString());
              }
              s.put("isSuccess", registerVoiceResult.getData().isSuccess());
              s.put("message", registerVoiceResult.getData().getMessage());
            } catch (Exception e) {
              Log.d("moesException ","Exception is"+ e);
            }

            String error = registerVoiceResult.getErrorMessage();
            if (error == null || error.isEmpty()) {
              callbackContext.sendPluginResult(
                new PluginResult(
                  PluginResult.Status.OK,
                  s));
            } else {
              callbackContext.sendPluginResult(
                new PluginResult(
                  PluginResult.Status.ERROR,
                  error));
            }
          }
        });
  }

  private void getVoiceEnableInfo(CallbackContext callbackContext) {
    Log.d("MoesgetVoiceEnableInfo", "getVoiceEnableInfo");

    AvailoSDKEngine.Profile.INSTANCE.getVoiceEnableInfo(
      new Continuation<UiResult<VoiceEnableInfo>>() {
        @NonNull
        @Override
        public CoroutineContext getContext() {
          return EmptyCoroutineContext.INSTANCE;
        }

        @Override
        public void resumeWith(@NonNull Object result) {
          UiResult<VoiceEnableInfo> voiceEnableInfo = ((UiResult<VoiceEnableInfo>) result);
          JSONObject s = new JSONObject();
          try {
            s.put("isVoiceEnabled", voiceEnableInfo.getData().isVoiceEnabled());
            s.put("Status", voiceEnableInfo.getData().getStatus());
            s.put("NumberOfVoice", voiceEnableInfo.getData().getVoicesCount());
            s.put("VoiceTexts", voiceEnableInfo.getData().getTextsList());
          } catch (JSONException e) {
            e.printStackTrace();
          }
          Log.d("moes", voiceEnableInfo.toString());
          String error = voiceEnableInfo.getErrorMessage();
          Log.d("moes", error);

          if (error == null || error.isEmpty()) {
            callbackContext.sendPluginResult(
              new PluginResult(
                PluginResult.Status.OK,
                s));
          } else {
            callbackContext.sendPluginResult(
              new PluginResult(
                PluginResult.Status.ERROR,
                error));
          }
        }
      });
  }

  private void validateRandomAttendance(CallbackContext callbackContext) {
    AvailoSDKEngine.Attendance.INSTANCE
      .validateRandomAttendance(
        new Continuation<UiResult<RandomAttendanceValidationResponse>>() {
          @NonNull
          @Override
          public CoroutineContext getContext() {
            return EmptyCoroutineContext.INSTANCE;
          }

          @Override
          public void resumeWith(@NonNull Object result) {
            UiResult<RandomAttendanceValidationResponse> validate = ((UiResult<RandomAttendanceValidationResponse>) result);
            JSONObject s = new JSONObject();
            try {
              s.put("Valid", validate.getData().isValid());
              s.put("ExpiredInMinutes", validate.getData().getExpiredInMinutes());
              s.put("SendTimeUtc", validate.getData().getSendTimeUtc());

            } catch (JSONException e) {
              e.printStackTrace();
            }
            String error = validate.getErrorMessage();
            if (error == null || error.isEmpty()) {
              callbackContext.sendPluginResult(
                new PluginResult(
                  PluginResult.Status.OK,
                  s));
            } else {
              callbackContext.sendPluginResult(
                new PluginResult(
                  PluginResult.Status.ERROR,
                  error));
            }
          }
        });
  }
  public void getUserProfile(CallbackContext callbackContext){
    AvailoSDKEngine.Profile.INSTANCE.getUserProfile(
      new Continuation<UiResult<ProfileResponse>>() {
        @NonNull
        @Override
        public CoroutineContext getContext() {
          return EmptyCoroutineContext.INSTANCE;
        }

        @Override
        public void resumeWith(@NonNull Object result) {
          UiResult<ProfileResponse> profile = ((UiResult<ProfileResponse>) result);
          JSONObject s = new JSONObject();
          try {
            s.put("faceBiometricsStatus", profile.getData().getFaceBiometricsStatus());
            s.put("voiceBiometricsStatus", profile.getData().getVoiceBiometricsStatus());

          } catch (JSONException e) {
            e.printStackTrace();
          }
          String error = profile.getErrorMessage();
          if (error == null || error.isEmpty()) {
            callbackContext.sendPluginResult(
              new PluginResult(
                PluginResult.Status.OK,
                s));
          } else {
            callbackContext.sendPluginResult(
              new PluginResult(
                PluginResult.Status.ERROR,
                error));
          }
        }
      }
    );
  }
}
