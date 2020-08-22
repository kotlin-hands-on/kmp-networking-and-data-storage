import SwiftUI
import shared

struct ContentView: View {
    let greeting = Greeting().greet()
    
    var body: some View {
        Text(greeting)
    }
}


