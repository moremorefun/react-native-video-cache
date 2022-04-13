require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "m-react-native-video-cache"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.description  = <<-DESC
                  m-react-native-video-cache
                   DESC
  s.homepage     = "https://github.com/moremorefun/react-native-video-cache"
  s.license      = "MIT"
  # s.license    = { :type => "MIT", :file => "FILE_LICENSE" }
  s.authors      = { "Vinothkumar Arputharaj" => "moremorefun@gmail.com" }
  s.platforms    = { :ios => "9.0" }
  s.source       = { :git => "https://github.com/moremorefun/react-native-video-cache.git", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,m,swift}"
  s.requires_arc = true

  s.dependency "React"
  s.dependency 'KTVHTTPCache', '~> 2.0.0'
  # ...
  # s.dependency "..."
end

