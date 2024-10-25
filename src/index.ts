import { registerPlugin } from '@capacitor/core';

import type { MockLocationCheckPlugin } from './definitions';

const MockLocationCheck = registerPlugin<MockLocationCheckPlugin>('MockLocationCheck', {
  web: () => import('./web').then((m) => new m.MockLocationCheckWeb()),
});

export * from './definitions';
export { MockLocationCheck };
