import {MockLocationCheck} from 'capacitor-mock-location-checker';

window.checkLocationMock = async () => {
    let isMock = await MockLocationCheck.isLocationMocked();
    document.getElementById("mock-value").innerText = isMock;
}
