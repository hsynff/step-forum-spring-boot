package com.step.forum.spring.repository;

import com.step.forum.spring.constants.TopicConstants;
import com.step.forum.spring.model.Comment;
import com.step.forum.spring.model.Topic;
import com.step.forum.spring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Repository
public class TopicRepositoryImpl implements TopicRepository {

    private final String GET_ALL_TOPIC_SQL = "select t.id_topic, t.title, t.description, " +
            "t.share_date, t.view_count, t.status, u.id_user, u.email, u.first_name, " +
            "u.last_name, u.img, c.id_comment, c.description, c.write_date " +
            "from topic t inner join user u on t.id_user = u.id_user " +
            "left join comment c on c.id_topic = t.id_topic " +
            "where t.status = ? order by t.share_date desc";

    private final String GET_TOPIC_BY_ID_SQL = "select t.id_topic, t.title, t.description as t_description, " +
            "t.share_date, t.view_count, t.status, u.id_user as t_id_user, u.first_name as t_first_name, " +
            "u.img as t_img, u.last_name as t_last_name " +
            "from topic t inner join user u on t.id_user=u.id_user " +
            "where t.id_topic=? and t.status = ? ";

    private final String GET_COMMENTS_BY_TOPIC_ID_SQL = "select * from comment c " +
            "inner join user u on c.id_user=u.id_user " +
            "where c.id_topic=? order by write_date asc";

    private final String GET_POPULAR_TOPICS_SQL = "select t.id_topic, t.title, count(c.id_comment) as comments " +
            "from topic t left join comment c on t.id_topic=c.id_topic " +
            "where t.status = ? " +
            "group by t.title having comments>0 " +
            "order by comments desc " +
            "limit 7";
    private final String ADD_TOPIC_SQL = "insert into topic(title, description, share_date, view_count, id_user, status) " +
            "values(?, ?, ?, ?, ?, ?)";

    private final String ADD_COMMENT_SQL = "insert into comment(description, write_date, id_topic, id_user) values(?,?,?,?)";

    private final String GET_TOPICS_BY_USER_ID_SQL = "select id_topic, title from topic where id_user=? and status = ? order by share_date desc limit 7";


    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Topic> getAllTopic() {
        List<Topic> list = jdbcTemplate.query(GET_ALL_TOPIC_SQL, new Object[]{TopicConstants.TOPIC_STATUS_ACTIVE}, new ResultSetExtractor<List<Topic>>() {
            @Nullable
            @Override
            public List<Topic> extractData(ResultSet rs) throws SQLException, DataAccessException {
                Map<Integer, Topic> map = new LinkedHashMap<>();
                while (rs.next()) {
                    Topic t = map.get(rs.getInt("id_topic"));

                    if (t == null) {
                        t = new Topic();
                        t.setId(rs.getInt("id_topic"));
                        t.setTitle(rs.getString("title"));
                        t.setDesc(rs.getString("description"));
                        t.setShareDate(rs.getTimestamp("share_date").toLocalDateTime());
                        t.setViewCount(rs.getInt("view_count"));
                        t.setStatus(rs.getInt("status"));

                        User u = new User();
                        u.setId(rs.getInt("id_user"));
                        u.setEmail(rs.getString("email"));
                        u.setFirstName(rs.getString("first_name"));
                        u.setLastName(rs.getString("last_name"));
                        u.setImagePath(rs.getString("img"));
                        t.setUser(u);
                        map.put(t.getId(), t);
                    }

                    if (rs.getInt("id_comment") != 0) {
                        Comment c = new Comment();
                        c.setId(rs.getInt("id_comment"));
                        c.setDesc(rs.getString("description"));
                        c.setWriteDate(rs.getTimestamp("write_date").toLocalDateTime());
                        t.addComment(c);
                    }
                }

                return new ArrayList<>(map.values());
            }
        });

        return list;
    }

    @Override
    public Topic getTopicById(int id) {
        Topic topic = jdbcTemplate.queryForObject(GET_TOPIC_BY_ID_SQL, new Object[]{id, TopicConstants.TOPIC_STATUS_ACTIVE}, new RowMapper<Topic>() {
            @Nullable
            @Override
            public Topic mapRow(ResultSet rs, int i) throws SQLException {
                Topic topic = new Topic();
                topic.setId(rs.getInt("id_topic"));
                topic.setTitle(rs.getString("title"));
                topic.setDesc(rs.getString("t_description"));
                topic.setShareDate(rs.getTimestamp("share_date").toLocalDateTime());
                topic.setViewCount(rs.getInt("view_count"));

                User user = new User();
                user.setId(rs.getInt("t_id_user"));
                user.setFirstName(rs.getString("t_first_name"));
                user.setLastName(rs.getString("t_last_name"));
                user.setImagePath(rs.getString("t_img"));
                topic.setUser(user);

                return topic;
            }
        });
        return topic;
    }

    @Override
    public List<Topic> getPopularTopics() {
       List<Topic> list = jdbcTemplate.query(GET_POPULAR_TOPICS_SQL, new Object[]{TopicConstants.TOPIC_STATUS_ACTIVE}, new RowMapper<Topic>() {
           @Nullable
           @Override
           public Topic mapRow(ResultSet rs, int i) throws SQLException {
               Topic topic = new Topic();
               topic.setId(rs.getInt("id_topic"));
               topic.setTitle(rs.getString("title"));
               topic.setCommentCount(rs.getInt("comments"));
               return topic;
           }
       });
       return list;
    }

    @Override
    public void addTopic(Topic topic) {
        // title, description, share_date, view_count, id_user, status
        jdbcTemplate.update(ADD_TOPIC_SQL, topic.getTitle(), topic.getDesc(), topic.getShareDate(), topic.getViewCount(), topic.getUser().getId(), topic.getStatus());
    }

    @Override
    public void updateTopicViewCount(int topicId) {

    }

    @Override
    public List<Topic> getSimilarTopics(String[] keywords) {
        return null;
    }

    @Override
    public List<Comment> getCommentsByTopicId(int id) {
       List<Comment> list = jdbcTemplate.query(GET_COMMENTS_BY_TOPIC_ID_SQL, new Object[]{id}, new RowMapper<Comment>() {
           @Nullable
           @Override
           public Comment mapRow(ResultSet rs, int i) throws SQLException {
               Comment comment = new Comment();
               comment.setId(rs.getInt("id_comment"));
               comment.setDesc(rs.getString("description"));
               comment.setWriteDate(rs.getTimestamp("write_date").toLocalDateTime());
               User user = new User();
               user.setId(rs.getInt("id_user"));
               user.setEmail(rs.getString("email"));
               user.setFirstName(rs.getString("first_name"));
               user.setLastName(rs.getString("last_name"));
               user.setImagePath(rs.getString("img"));
               comment.setUser(user);
               return comment;
           }
       });

       return list;
    }

    @Override
    public void addComment(Comment comment) {

        jdbcTemplate.update(ADD_COMMENT_SQL, comment.getDesc(), comment.getWriteDate(), comment.getTopic().getId(), comment.getUser().getId());
    }

    @Override
    public List<Topic> getTopicsByUserId(int idUser) {
        List<Topic> list = jdbcTemplate.query(GET_TOPICS_BY_USER_ID_SQL, new Object[]{idUser, 1}, new RowMapper<Topic>() {
            @Nullable
            @Override
            public Topic mapRow(ResultSet rs, int i) throws SQLException {
                Topic topic = new Topic();
                topic.setId(rs.getInt("id_topic"));
                topic.setTitle(rs.getString("title"));
                return topic;
            }
        });
        return list;
    }
}
