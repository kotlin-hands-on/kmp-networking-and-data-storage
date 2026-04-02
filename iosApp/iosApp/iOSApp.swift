import SwiftUI
import SharedLogic

@main
struct iOSApp: App {
    init() {
        KoinHelperKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView(viewModel: .init())
        }
    }
}