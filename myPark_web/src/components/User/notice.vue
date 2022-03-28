<template>
  <a-list
    class="comment-list"
    :header="`${data.length} 条公告`"
    item-layout="horizontal"
    :data-source="data"
    style="margin-right:10%;margin-left:10% "
  >
    <a-list-item slot="renderItem" slot-scope="item, index">
      <a-comment :author="item.author" :avatar="item.avatar">

        <p slot="content">
          {{ item.content }}
        </p>
        <a-tooltip slot="datetime" :title="item.datetime.format('YYYY-MM-DD HH:mm:ss')">
          <span>{{ item.datetime.fromNow() }}</span>
        </a-tooltip>
      </a-comment>
    </a-list-item>
  </a-list>
</template>

<script>
  import moment from 'moment';
  export default {
    name:'notice',
    data() {
      return {
        data: [
          {
            actions: ['Reply to'],
            author: 'Han Solo',
            avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
            content:
              'We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.',
            datetime: moment().subtract(1, 'days'),
          },
          {
            actions: ['Reply to'],
            author: 'Han Solo',
            avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
            content:
              'We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.',
            datetime: moment().subtract(2, 'days'),
          },
        ],
        moment,
      };
    },
    methods:{

    }
  }
</script>


<style  scoped>

</style>

