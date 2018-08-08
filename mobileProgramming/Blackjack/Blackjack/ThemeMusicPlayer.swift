//
//  ThemeMusicPlayer.swift
//  Blackjack
//
//  Created by Guillermo Moran on 2/22/17.
//  Copyright © 2017 Guillermo Moran. All rights reserved.
//

import UIKit

import AVFoundation

class ThemeMusicPlayer: NSObject {
    
    private static var player: AVAudioPlayer?
    
    static func playMusic() {
        
        let url = Bundle.main.url(forResource: "theme", withExtension: "mp3")!
        
        do {
            
            player = try AVAudioPlayer(contentsOf: url)
            player?.numberOfLoops = -1
            guard let player = player else { return }
            
            player.prepareToPlay()
            player.play()
            
        }
        catch let error {
            
            print(error.localizedDescription)
        }
    }
    
    static func stopMusic() {
        player?.stop()
    }

}
