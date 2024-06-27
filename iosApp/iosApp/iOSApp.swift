import SwiftUI
import Shared

@main
struct iOSApp: App {
    init() {
        KoinHelper().doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView(viewModel: .init())
        }
    }
}
