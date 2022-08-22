import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'test_platform_interface.dart';

/// An implementation of [TestPlatform] that uses method channels.
class MethodChannelTest extends TestPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('test');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
   @override
  Future<String?> getData(userid) async {
    final version = await methodChannel.invokeMethod<String>('getData',{"userid":userid});
    return version;
  }
}
