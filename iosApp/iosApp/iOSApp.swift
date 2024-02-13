import SwiftUI
import Shared

@main
struct iOSApp: App {
    let sdk = SpaceXSDK(databaseDriverFactory: IOSDatabaseDriverFactory(), api: SpaceXApi())
    var body: some Scene {
        WindowGroup {
            ContentView(viewModel: .init(sdk: sdk))
        }
    }
}
