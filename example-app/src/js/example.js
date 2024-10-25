import { MockLocationCheck } from 'capacitor-mock-location-checker';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    MockLocationCheck.echo({ value: inputValue })
}
