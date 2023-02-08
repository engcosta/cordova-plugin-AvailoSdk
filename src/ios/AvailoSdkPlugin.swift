import Foundation
import AvailoSDKEngine


@objc(AvailoSdkPlugin) class AvailoSdkPlugin : CDVPlugin{



    override func pluginInitialize() {
          super.pluginInitialize()
           print("pluginInitialize")
      }



   @objc(initSDK:)
    func initSDK(_ command: CDVInvokedUrlCommand){

        AvailoSDKEngine().initialize { result in
            switch result {

            case .success(let response):
                               if let message = response as? String {
                                   let pluginResult = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: message);
                                   self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
                               }else{
                                   let pluginResult = CDVPluginResult(status: CDVCommandStatus_OK, messageAs:true);
                                   self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
                               }

                case .failure(let error):
                let pluginResult = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: "\(error)" );
                self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
            }
        }

    }

 @objc(loginByCredentials:)
    func loginByCredentials(_ command: CDVInvokedUrlCommand){

      //  var pluginResult = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: "tuqa sawalha");

        let arr : NSArray = command.arguments[0] as! NSArray
                AvailoSDKEngine().loginByCredentialsData(
                    arr[0] as! String,
                    arr[1] as! String
                ) { result in
                    switch result {
                    case .success(let response):
                        if let message = response as? String {
                            let pluginResult = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: message);
                            self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
                            }else{
                            if let jsonResult = response as? Dictionary<String, AnyObject> {
                            let pluginResult = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: jsonResult);
                            self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
                                }
                            }

                    case .failure(let error):
                    let pluginResult = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: "\(error)" );
                    self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
                    }
                }


       // commandDelegate.send(pluginResult, callbackId:command.callbackId);
    }
      @objc(attendByFace:)
    func attendByFace(_ command: CDVInvokedUrlCommand){
        let arr : NSArray = command.arguments[0] as! NSArray
        AvailoSDKEngine().AttendFaceData(
                                arr[0] as! String,arr[1] as! String
                            ) { result in
                                switch result {
                                    case .success(let response):
                                    if let jsonResult = response as? Dictionary<String, AnyObject> {
                                        let pluginResult = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: jsonResult);
                                        self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
                                    }
                                case .failure(let error):
                                let pluginResult = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: "\(error)" );
                                self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
                                }
                            }
    }

 @objc(getRandomTextForAttendance:)
    func getRandomTextForAttendance(_ command: CDVInvokedUrlCommand){

       AvailoSDKEngine().getRandomTextData { result in
           switch result {

               case .success(let response):
               if let message = response as? String {
                   let pluginResult = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: message);
                   self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
                   }else{
                       if let jsonResult = response as? Array<Any> {
                           let pluginResult = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: jsonResult);
                           self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
                       }
                   }
           case .failure(let error):
           let pluginResult = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: "\(error)" );
           self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
           }
       }

    }

      @objc(attendByVoice:)
    func attendByVoice(_ command: CDVInvokedUrlCommand){
        let arr : NSArray = command.arguments[0] as! NSArray
        AvailoSDKEngine().AttendVoiceData(arr[0] as! String,arr[1] as! String,arr[2] as! String)
        { result in
            switch result {
                case .success(let response):
                if let jsonResult = response as? Dictionary<String, AnyObject> {
                    let pluginResult = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: jsonResult);
                    self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
                }
                case .failure(let error):
                let pluginResult = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: "\(error)" );
                self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
            }
        }
    }

     @objc(getLastTransaction:)
    func getLastTransaction(_ command: CDVInvokedUrlCommand){

       AvailoSDKEngine().getLastTransactionsByUserIDData { result in
           switch result {
               case .success(let response):
               if let message = response as? String {
                   let pluginResult = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: message);
                   self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
                   }else{
                       if let jsonResult = response as? Dictionary<String, AnyObject> {
                           let pluginResult = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: jsonResult);
                           self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
                       }
                   }
           case .failure(let error):
           let pluginResult = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: "\(error)" );
           self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
           }
       }

    }

      @objc(getRequiredFilesCount:)
    func getRequiredFilesCount(_ command: CDVInvokedUrlCommand){
             AvailoSDKEngine().getFilesCountByUserData { result in
           switch result {
           case .success(let response):
               if let message = response as? String {
                   let pluginResult = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: message);
                   self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
                   }else{
                       if let jsonResult = response as? Dictionary<String, AnyObject> {
                           let pluginResult = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: jsonResult);
                           self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
                       }
                   }

           case .failure(let error):
           let pluginResult = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: "\(error)" );
           self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
           }
       }
    }

          @objc(registerImage:)
    func registerImage(_ command: CDVInvokedUrlCommand){
        let arr : NSArray = command.arguments[0] as! NSArray
        AvailoSDKEngine().registerImageData(
                                arr[0] as! String) { result in
                                switch result {
                                case .success(let response):
                                    if let jsonResult = response as? Dictionary<String, AnyObject> {
                                        let pluginResult = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: jsonResult);
                                        self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
                                    }
                                case .failure(let error):
                                let pluginResult = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: "\(error)" );
                                self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
                                }
                            }
    }
  @objc(getRandomTextForSignup:)
    func getRandomTextForSignup(_ command: CDVInvokedUrlCommand){
             AvailoSDKEngine().getRandomVoiceTextData { result in
           switch result {
           case .success(let response):
               if let message = response as? String {
                   let pluginResult = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: message);
                   self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
                   }else{
                    if let jsonResult = response as?  Array<Any>  {
                        let pluginResult = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: jsonResult);
                        self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
                    }
                   }

           case .failure(let error):
           let pluginResult = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: "\(error)" );
           self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
           }
       }
    }

      @objc(registerVoiceFile:)
    func registerVoiceFile(_ command: CDVInvokedUrlCommand){
        let arr : NSArray = command.arguments[0] as! NSArray
        AvailoSDKEngine().registerVoiceData(arr[0] as! String,arr[1] as! String)
        { result in
            switch result {
                case .success(let response):
                if let jsonResult = response as? Dictionary<String, AnyObject> {
                    let pluginResult = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: jsonResult);
                    self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
                }
            case .failure(let error):
            let pluginResult = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: "\(error)" );
            self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
            }
        }
    }
      @objc(changeUserStatus:)
    func changeUserStatus(_ command: CDVInvokedUrlCommand){
                 AvailoSDKEngine().changeUserStatesData { result in
                     switch result {
                         case .success(let response):
                         if let jsonResult = response as? Dictionary<String, AnyObject> {
                             let pluginResult = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: jsonResult);
                             self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
                         }

                     case .failure(let error):
                     let pluginResult = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: "\(error)" );
                     self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
                     }
                 }
}
  @objc(getUserProfile:)
    func getUserProfile(_ command: CDVInvokedUrlCommand){
             AvailoSDKEngine().getUserProfileData { result in
           switch result {
           case .success(let response):
            if let jsonResult = response as?  Dictionary<String, AnyObject> {
                let pluginResult = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: jsonResult);
                self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
            }
           case .failure(let error):
           let pluginResult = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: "\(error)" );
           self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
           }
       }
    }

    @objc(getVoiceEnableInfo:)
    func getVoiceEnableInfo(_ command: CDVInvokedUrlCommand){
             AvailoSDKEngine().getVoiceEnableInfoData { result in
           switch result {
           case .success(let response):
               if let message = response as? String {
                   let pluginResult = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: message);
                   self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
                   }else{
                       if let jsonResult = response as?  Dictionary<String, AnyObject> {
                           let pluginResult = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: jsonResult);
                           self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
                       }
                   }

           case .failure(let error):
           let pluginResult = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: "\(error)" );
           self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
           }
       }
    }
    @objc(addNewFaceFile:)
    func addNewFaceFile(_ command: CDVInvokedUrlCommand){
        let arr : NSArray = command.arguments[0] as! NSArray
        AvailoSDKEngine().addNewFaceData(
                                arr[0] as! String) { result in
           switch result {
           case .success(let response):
            if let jsonResult = response as?  Dictionary<String, AnyObject> {
                let pluginResult = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: jsonResult);
                self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
            }
           case .failure(let error):
           let pluginResult = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: "\(error)" );
           self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
           }
       }
    }

      @objc(addNewVoiceFile:)
    func addNewVoiceFile(_ command: CDVInvokedUrlCommand){
        let arr : NSArray = command.arguments[0] as! NSArray
        AvailoSDKEngine().addNewVoiceData(arr[0] as! String,arr[1] as! String)
        { result in
            switch result {
           case .success(let response):
            if let jsonResult = response as?  Dictionary<String, AnyObject> {
                let pluginResult = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: jsonResult);
                self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
            }
            case .failure(let error):
            let pluginResult = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: "\(error)" );
            self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
           }
        }
    }

      @objc(validateRandomAttendance:)
    func validateRandomAttendance(_ command: CDVInvokedUrlCommand){
             AvailoSDKEngine().validateRandomAttendanceData { result in
           switch result {
           case .success(let response):
               if let message = response as? String {
                   let pluginResult = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: message);
                   self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
                   }else{
                       if let jsonResult = response as?  Dictionary<String, AnyObject> {
                           let pluginResult = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: jsonResult);
                           self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
                       }
                   }


           case .failure(let error):
           let pluginResult = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: "\(error)" );
           self.commandDelegate.send(pluginResult, callbackId:command.callbackId);
           }
       }
    }
    }


