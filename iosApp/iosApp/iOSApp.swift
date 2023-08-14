import SwiftUI
import shared

@main
struct iOSApp: App {
    
    init() {
        DependencyInjectionKt.doInitModule()
    }
    
	var body: some Scene {
		WindowGroup {
			ContentView().environment(\.colorScheme, .dark)
		}
	}
}
