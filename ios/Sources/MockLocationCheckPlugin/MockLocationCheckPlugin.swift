import Capacitor
import CoreLocation
import Foundation

/// Please read the Capacitor iOS Plugin Development Guide
/// here: https://capacitorjs.com/docs/plugins/ios
@objc(MockLocationCheckPlugin)
public class MockLocationCheckPlugin: CAPPlugin, CAPBridgedPlugin, CLLocationManagerDelegate {
  public let identifier = "MockLocationCheckPlugin"
  public let jsName = "MockLocationCheck"
  public let pluginMethods: [CAPPluginMethod] = [
    CAPPluginMethod(name: "isLocationMocked", returnType: CAPPluginReturnPromise)
  ]
    
  private var locationManager: CLLocationManager!
  private var call: CAPPluginCall?

  @objc func isLocationMocked(_ call: CAPPluginCall) {
    self.call = call

    DispatchQueue.main.async {
      self.locationManager = CLLocationManager()
      self.locationManager.delegate = self
      self.locationManager.desiredAccuracy = kCLLocationAccuracyBest

      let status = CLLocationManager.authorizationStatus()
      if status == .notDetermined {
        self.locationManager.requestWhenInUseAuthorization()
      } else if status == .authorizedWhenInUse || status == .authorizedAlways {
        self.locationManager.requestLocation()
      } else {
        call.reject("Location permissions not granted.")
      }
    }
  }

  public func locationManager(
    _ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]
  ) {
    guard let location = locations.last else {
      call?.reject("Unable to obtain location.")
      return
    }
      let isSimulated = location.isSimulatedBySoftware
      let result = ["isSimulated": isSimulated]
      call?.resolve(result)
  }

  public func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
    call?.reject("Location error: \(error.localizedDescription)")
  }

  public func locationManager(
    _ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus
  ) {
    if status == .authorizedWhenInUse || status == .authorizedAlways {
      manager.requestLocation()
    } else if status == .denied || status == .restricted {
      call?.reject("Location permissions not granted.")
    }
  }

}
