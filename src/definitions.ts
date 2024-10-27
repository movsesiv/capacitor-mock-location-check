export interface MockLocationCheckPlugin {
  isLocationMocked(): Promise<{isLocationMocked: boolean}>;
}
