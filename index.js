import { NativeModules } from 'react-native';

export default (url) => {
  if (!global.nativeCallSyncHook) {
    return url
  }
  return NativeModules.VideoCache.convert(url)
};

export const convertAsync = NativeModules.VideoCache.convertAsync;

/**
 * 
 * @param {*} options | {fileNameGeneratorClassName: string, maxCacheSize: number}
 * @returns 
 */
export const setConfig = (options) => {
  if (Platform.OS === "web") {
    return;
  }
  return NativeModules.VideoCache.setConfig(options);
};
