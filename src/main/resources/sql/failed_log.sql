drop table if exists public.failed_log;
create table public.failed_log(
  ID BIGSERIAL PRIMARY KEY,
  created_time TIMESTAMP,
  update_time TIMESTAMP,
  VERSION INTEGER DEFAULT 0,
  retry_status VARCHAR(50),
  valid_status VARCHAR(50),
  retry_times int,
  error_stack text,
  retry_success_key_id bigint,
  context_class VARCHAR(300),
  context_value text,
  core_business_class VARCHAR(300)
);

--表说明
COMMENT ON TABLE public.failed_log IS '用户注册失败记录表';
--字段说明
COMMENT ON COLUMN public.failed_log.ID IS '主键ID';
COMMENT ON COLUMN public.failed_log.created_time IS '记录创建时间（UTC）';
COMMENT ON COLUMN public.failed_log.update_time IS '记录更新时间（UTC）';
COMMENT ON COLUMN public.failed_log.VERSION IS '记录更新版本号';
COMMENT ON COLUMN public.failed_log.retry_status IS '重试状态';
COMMENT ON COLUMN public.failed_log.valid_status IS '有效状态';
COMMENT ON COLUMN public.failed_log.retry_times IS '重试次数';
COMMENT ON COLUMN public.failed_log.error_stack IS '最新一次失败的异常堆栈';
COMMENT ON COLUMN public.failed_log.retry_success_key_id IS '重试成功后的关键id';
COMMENT ON COLUMN public.failed_log.context_class IS '参数context的class类';
COMMENT ON COLUMN public.failed_log.context_value IS '参数context的值';
COMMENT ON COLUMN public.failed_log.core_business_class IS 'core bussiness的class类';