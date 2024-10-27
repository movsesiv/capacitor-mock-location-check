import { WebPlugin } from '@capacitor/core';

import type { MockLocationCheckPlugin } from './definitions';

export class MockLocationCheckWeb extends WebPlugin implements MockLocationCheckPlugin {
  async isLocationMocked(): Promise<boolean> {
    console.log('isLocationMocked', 'MockLocationCheck is not supported on the web');
    return false;
  }
}
