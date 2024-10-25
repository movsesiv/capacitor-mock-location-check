import {WebPlugin} from '@capacitor/core';

import type {MockLocationCheckPlugin} from './definitions';

export class MockLocationCheckWeb extends WebPlugin implements MockLocationCheckPlugin {
    async isLocationMocked(): Promise<boolean> {
        console.log('MockLocationCheckPlugin is not supported on web.')
        return false;
    }

}
