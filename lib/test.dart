import 'test_platform_interface.dart';

class Test {
  Future<String?> getPlatformVersion() {
    return TestPlatform.instance.getPlatformVersion();
  }

  Future<String?> getData(userid) {
    return TestPlatform.instance.getData(userid);
  }
}
