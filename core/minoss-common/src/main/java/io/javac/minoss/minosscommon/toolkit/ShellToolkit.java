package io.javac.minoss.minosscommon.toolkit;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Optional;

/**
 * @author pencilso
 * @date 2020/2/27 6:09 下午
 */
@Slf4j
public class ShellToolkit {

    /**
     * 执行系统命令, 返回执行结果
     *
     * @param cmd 需要执行的命令
     * @param dir 执行命令的子进程的工作目录, null 表示和当前主进程工作目录相同
     */
    public static Optional<String> execCmd(String cmd, File dir) {


        StringBuilder result = new StringBuilder();
        String resultStr = null;
        Process process = null;
        BufferedReader bufrIn = null;
        BufferedReader bufrError = null;

        try {
            // 执行命令, 返回一个子进程对象（命令在子进程中执行）
            process = Runtime.getRuntime().exec(cmd, null, dir);

            // 方法阻塞, 等待命令执行完成（成功会返回0）
            process.waitFor();

            // 获取命令执行结果, 有两个结果: 正常的输出 和 错误的输出（PS: 子进程的输出就是主进程的输入）
            bufrIn = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            bufrError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "UTF-8"));

            // 读取输出
            String line = null;
            while ((line = bufrIn.readLine()) != null) {
                result.append(line).append('\n');
            }
            while ((line = bufrError.readLine()) != null) {
                result.append(line).append('\n');
            }
            resultStr = result.toString();
        } catch (Exception ex) {
            log.warn("exec cmd error cmd: [{}] dir: [{}]", cmd, dir);
        } finally {
            StreamToolkit.closeStream(bufrIn, bufrError);
            // 销毁子进程
            if (process != null) {
                process.destroy();
            }
        }
        log.debug("exec cmd  cmd: [{}] dir: [{}] responeout: [{}]", cmd, dir,result);
        // 返回执行结果
        return Optional.ofNullable(resultStr);
    }
}
