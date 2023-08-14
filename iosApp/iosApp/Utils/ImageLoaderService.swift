//
//  ImageLoaderService.swift
//  iosApp
//
//  Created by Achmad Tasa Farian on 21/07/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import UIKit

class ImageLoaderService: ObservableObject {
    @Published var image = UIImage()
    
    convenience init(url: URL) {
        self.init()
        loadImage(for: url)
    }
    
    func loadImage(for url: URL) {
        let task = URLSession.shared.dataTask(with: url) { data, _, _ in
            guard let data = data else { return }
            DispatchQueue.main.async {
                self.image = UIImage(data: data) ?? UIImage()
            }
        }
        task.resume()
    }
}
