import {MockLocationCheck} from 'capacitor-mock-location-check';

window.checkLocationMock = async () => {
    let result = await MockLocationCheck.isLocationMocked();
    document.getElementById("mock-value").innerText = result.isLocationMocked;
}
