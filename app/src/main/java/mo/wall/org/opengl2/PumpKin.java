package mo.wall.org.opengl2;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * 这应该是opengl1.0的代码，暂时不用
 * https://blog.csdn.net/qq_31726827/article/details/51265186
 * Created by Administrator on 2016/4/28.
 */
public class PumpKin {

    private FloatBuffer vertexsBuffer;
    private FloatBuffer colorsBuffer;

    private ByteBuffer indicesBuffer;

    private float vertexs[] = {
            0.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 1.0f
    };

    private float colors[] = {
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f
    };

    private byte indices[] = {0, 1, 2};

    public PumpKin() {

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertexs.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vertexsBuffer = vbb.asFloatBuffer();
        vertexsBuffer.put(vertexs);
        vertexsBuffer.position(0);

        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
        cbb.order(ByteOrder.nativeOrder());
        colorsBuffer = cbb.asFloatBuffer();
        colorsBuffer.put(colors);
        colorsBuffer.position(0);

    }

    public void draw(GL10 gl) {

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexsBuffer);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorsBuffer);

        gl.glDrawArrays(GL10.GL_LINES, 0, vertexs.length / 3);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

    }

}