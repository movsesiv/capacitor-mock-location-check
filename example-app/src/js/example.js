import {MockLocationCheck} from 'capacitor-mock-location-check';

window.checkLocationMock = async () => {
    let isMock = await MockLocationCheck.isLocationMocked();
    document.getElementById("mock-value").innerText = isMock;
}
