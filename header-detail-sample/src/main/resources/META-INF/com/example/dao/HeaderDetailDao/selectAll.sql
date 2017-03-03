with detail_ as (
select id
     , parent
     , name
     , min(id) over(partition by parent) as first_id
     , max(id) over(partition by parent) as last_id
  from detail
)
    select case when d.id = d.first_id then h.id else null end as header_id
         , case when d.id = d.first_id then h.name else null end as header_name
         , d.id as detail_id
         , d.name as detail_name
         , d.first_id
         , d.last_id
      from header h
inner join detail_ d
        on d.parent = h.id
  order by h.id asc
         , d.id asc
