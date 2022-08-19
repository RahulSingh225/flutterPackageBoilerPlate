
import 'test_platform_interface.dart';

class Test {
  Future<String?> getPlatformVersion() {
    return TestPlatform.instance.getPlatformVersion();
  }
}
