import type { HookAPI } from "@oh-my-pi/pi-coding-agent/extensibility/hooks";

export default function (pi: HookAPI): void {
  pi.on("session_start", async () => {
    pi.sendMessage({
      role: "custom",
      customType: "spec-superflow-context",
      content: [
        {
          type: "text",
          text: "spec-superflow 工作流已加载。使用 /skill:workflow-start 或说「用 workflow-start 开始」启动 Spec-first 开发流程。",
        },
      ],
    });
  });
}
