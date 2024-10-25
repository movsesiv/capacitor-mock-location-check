// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorMockLocationChecker",
    platforms: [.iOS(.v13)],
    products: [
        .library(
            name: "CapacitorMockLocationChecker",
            targets: ["MockLocationCheckPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", branch: "main")
    ],
    targets: [
        .target(
            name: "MockLocationCheckPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/MockLocationCheckPlugin"),
        .testTarget(
            name: "MockLocationCheckPluginTests",
            dependencies: ["MockLocationCheckPlugin"],
            path: "ios/Tests/MockLocationCheckPluginTests")
    ]
)