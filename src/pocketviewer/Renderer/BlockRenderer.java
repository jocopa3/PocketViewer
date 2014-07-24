package pocketviewer.Renderer;

import static pocketviewer.Objects.BlockFace.*;
import pocketviewer.Objects.Face;
import pocketviewer.Blocks.*;

/**
 * Needs to include handling for specific blocks later
 *
 * @author Jocopa3
 */
public class BlockRenderer {
    //Block light constants; easy to change for experimenting with lighting
    public static final float lr = 0.6f; //Left-Right light intensity constant
    public static final float fb = 0.8f; //Front-Back light intensity constant
    public static final float bo = 0.5f; //Bottom light intensity constant
    public static final float to = 1f; //Toplight intensity constant
    public static final float inc = 1/15f; //Light increment constant
    public static final float sh = 0.4f; //Shadow constant
    
    //Blank initializer for now
    public BlockRenderer() {}

    public static Vertex[][] get(int id, float x, float y, float z) {
        if (Block.blocks[id] != null && Block.blocks[id].isCube) {
            return getCuboid(id, x, y, z);
        } else {
            return getCuboid(0, x, y, z); //Default is air for now
        }
    }

    public static Vertex[][] getCuboid(int id, float x, float y, float z) {
        Face[] faceTextures = Block.blocks[id].faces;
        Vertex[][] faces = new Vertex[TOTAL_FACES][4];

        faces[TOP] = getTop(faceTextures[TOP], x, y, z);
        faces[BOTTOM] = getBottom(faceTextures[BOTTOM], x, y, z);
        faces[LEFT] = getLeft(faceTextures[LEFT], x, y, z);
        faces[RIGHT] = getRight(faceTextures[RIGHT], x, y, z);
        faces[FRONT] = getFront(faceTextures[FRONT], x, y, z);
        faces[BACK] = getBack(faceTextures[BACK], x, y, z);

        return faces;
    }

    public static Vertex[] getFront(Face face, float x, float y, float z) {
        return getFront(face, x, y, z, 15);
    }

    public static Vertex[] getFront(Face face, float x, float y, float z, int light) {
        face.setLight(fb, light);

        Vertex v1, v2, v3, v4;
        v1 = new Vertex(x, y + 1, z, face.r, face.g, face.b, face.minU, face.minV); //x,y,z,r,g,b,u,v
        v2 = new Vertex(x, y, z, face.r, face.g, face.b, face.minU, face.maxV);
        v3 = new Vertex(x + 1, y, z, face.r, face.g, face.b, face.maxU, face.maxV);
        v4 = new Vertex(x + 1, y + 1, z, face.r, face.g, face.b, face.maxU, face.minV);
        return new Vertex[]{v1, v2, v3, v4};
    }

    public static Vertex[] getBack(Face face, float x, float y, float z) {
        return getBack(face, x, y, z, 15);
    }

    public static Vertex[] getBack(Face face, float x, float y, float z, int light) {
        face.setLight(fb, light);

        Vertex v1, v2, v3, v4;
        v1 = new Vertex(x + 1, y + 1, z + 1, face.r, face.g, face.b, face.minU, face.minV);
        v2 = new Vertex(x + 1, y, z + 1, face.r, face.g, face.b, face.minU, face.maxV);
        v3 = new Vertex(x, y, z + 1, face.r, face.g, face.b, face.maxU, face.maxV);
        v4 = new Vertex(x, y + 1, z + 1, face.r, face.g, face.b, face.maxU, face.minV);
        return new Vertex[]{v1, v2, v3, v4};
    }

    public static Vertex[] getLeft(Face face, float x, float y, float z) {
        return getLeft(face, x, y, z, 15);
    }

    public static Vertex[] getLeft(Face face, float x, float y, float z, int light) {
        face.setLight(lr, light);

        Vertex v1, v2, v3, v4;
        v1 = new Vertex(x, y + 1, z + 1, face.r, face.g, face.b, face.minU, face.minV);
        v2 = new Vertex(x, y, z + 1, face.r, face.g, face.b, face.minU, face.maxV);
        v3 = new Vertex(x, y, z, face.r, face.g, face.b, face.maxU, face.maxV);
        v4 = new Vertex(x, y + 1, z, face.r, face.g, face.b, face.maxU, face.minV);
        return new Vertex[]{v1, v2, v3, v4};
    }

    public static Vertex[] getRight(Face face, float x, float y, float z) {
        return getRight(face, x, y, z, 15);
    }

    public static Vertex[] getRight(Face face, float x, float y, float z, int light) {
        face.setLight(lr, light);

        Vertex v1, v2, v3, v4;
        v1 = new Vertex(x + 1, y + 1, z, face.r, face.g, face.b, face.minU, face.minV);
        v2 = new Vertex(x + 1, y, z, face.r, face.g, face.b, face.minU, face.maxV);
        v3 = new Vertex(x + 1, y, z + 1, face.r, face.g, face.b, face.maxU, face.maxV);
        v4 = new Vertex(x + 1, y + 1, z + 1, face.r, face.g, face.b, face.maxU, face.minV);
        return new Vertex[]{v1, v2, v3, v4};
    }

    public static Vertex[] getTop(Face face, float x, float y, float z) {
        return getTop(face, x, y, z, 15);
    }

    public static Vertex[] getTop(Face face, float x, float y, float z, int light) {
        face.setLight(to, light);

        Vertex v1, v2, v3, v4;
        v1 = new Vertex(x, y + 1, z + 1, face.r, face.g, face.b, face.minU, face.minV);
        v2 = new Vertex(x, y + 1, z, face.r, face.g, face.b, face.minU, face.maxV);
        v3 = new Vertex(x + 1, y + 1, z, face.r, face.g, face.b, face.maxU, face.maxV);
        v4 = new Vertex(x + 1, y + 1, z + 1, face.r, face.g, face.b, face.maxU, face.minV);
        return new Vertex[]{v1, v2, v3, v4};
    }

    public static Vertex[] getBottom(Face face, float x, float y, float z) {
        return getBottom(face, x, y, z, 15);
    }

    public static Vertex[] getBottom(Face face, float x, float y, float z, int light) {
        face.setLight(bo, light);

        Vertex v1, v2, v3, v4;
        v1 = new Vertex(x, y, z, face.r, face.g, face.b, face.minU, face.minV);
        v2 = new Vertex(x, y, z + 1, face.r, face.g, face.b, face.minU, face.maxV);
        v3 = new Vertex(x + 1, y, z + 1, face.r, face.g, face.b, face.maxU, face.maxV);
        v4 = new Vertex(x + 1, y, z, face.r, face.g, face.b, face.maxU, face.minV);
        return new Vertex[]{v1, v2, v3, v4};
    }

    public static Vertex[] getFront(int id, float x, float y, float z, int light) {
        Block block = Block.blocks[id];
        if (block == null) {
            return getFront(Block.blocks[0].getFace(FRONT), x, y, z, light);
        } else {
            return getFront(block.getFace(FRONT), x, y, z, light);
        }
    }

    public static Vertex[] getBack(int id, float x, float y, float z, int light) {
        Block block = Block.blocks[id];
        if (block == null) {
            return getBack(Block.blocks[0].getFace(BACK), x, y, z, light);
        } else {
            return getBack(block.getFace(BACK), x, y, z, light);
        }
    }

    public static Vertex[] getLeft(int id, float x, float y, float z, int light) {
        Block block = Block.blocks[id];
        if (block == null) {
            return getLeft(Block.blocks[0].getFace(LEFT), x, y, z, light);
        } else {
            return getLeft(block.getFace(LEFT), x, y, z, light);
        }
    }

    public static Vertex[] getRight(int id, float x, float y, float z, int light) {
        Block block = Block.blocks[id];
        if (block == null) {
            return getRight(Block.blocks[0].getFace(RIGHT), x, y, z, light);
        } else {
            return getRight(block.getFace(RIGHT), x, y, z, light);
        }
    }

    public static Vertex[] getTop(int id, float x, float y, float z, int light) {
        Block block = Block.blocks[id];
        if (block == null) {
            return getTop(Block.blocks[0].getFace(TOP), x, y, z, light);
        } else {
            return getTop(block.getFace(TOP), x, y, z, light);
        }
    }

    public static Vertex[] getBottom(int id, float x, float y, float z, int light) {
        Block block = Block.blocks[id];
        if (block == null) {
            return getBottom(Block.blocks[0].getFace(BOTTOM), x, y, z, light);
        } else {
            return getBottom(block.getFace(BOTTOM), x, y, z, light);
        }
    }
    
    
    //Ambient Occlusion (smooth lighting) methods
    public static Vertex[] getFrontWithAO(Face face, float x, float y, float z) {
        return getFront(face, x, y, z, 15);
    }

    public static Vertex[] getFrontWithAO(Face face, float x, float y, float z, int light, boolean[] opaque) {
        Vertex v1, v2, v3, v4;
        face.setLight(fb-sh*(opaque[5]||opaque[6]||opaque[7]?1:0), light);
        v1 = new Vertex(x, y + 1, z, face.r, face.g, face.b, face.minU, face.minV);
        face.setLight(fb-sh*(opaque[7]||opaque[0]||opaque[1]?1:0), light);
        v2 = new Vertex(x, y, z, face.r, face.g, face.b, face.minU, face.maxV);
        face.setLight(fb-sh*(opaque[1]||opaque[2]||opaque[3]?1:0), light);
        v3 = new Vertex(x + 1, y, z, face.r, face.g, face.b, face.maxU, face.maxV);
        face.setLight(fb-sh*(opaque[3]||opaque[4]||opaque[5]?1:0), light);
        v4 = new Vertex(x + 1, y + 1, z, face.r, face.g, face.b, face.maxU, face.minV);
        return new Vertex[]{v1, v2, v3, v4};
    }

    public static Vertex[] getBackWithAO(Face face, float x, float y, float z, boolean[] opaque) {
        return getBackWithAO(face, x, y, z, 15, opaque);
    }

    public static Vertex[] getBackWithAO(Face face, float x, float y, float z, int light, boolean[] opaque) {
        Vertex v1, v2, v3, v4;
        face.setLight(fb-sh*(opaque[3]||opaque[4]||opaque[5]?1:0), light);
        v1 = new Vertex(x + 1, y + 1, z + 1, face.r, face.g, face.b, face.minU, face.minV);
        face.setLight(fb-sh*(opaque[1]||opaque[2]||opaque[3]?1:0), light);
        v2 = new Vertex(x + 1, y, z + 1, face.r, face.g, face.b, face.minU, face.maxV);
        face.setLight(fb-sh*(opaque[7]||opaque[0]||opaque[1]?1:0), light);
        v3 = new Vertex(x, y, z + 1, face.r, face.g, face.b, face.maxU, face.maxV);
        face.setLight(fb-sh*(opaque[5]||opaque[6]||opaque[7]?1:0), light);
        v4 = new Vertex(x, y + 1, z + 1, face.r, face.g, face.b, face.maxU, face.minV);
        return new Vertex[]{v1, v2, v3, v4};
    }

    public static Vertex[] getLeftWithAO(Face face, float x, float y, float z, boolean[] opaque) {
        return getLeftWithAO(face, x, y, z, 15, opaque);
    }

    public static Vertex[] getLeftWithAO(Face face, float x, float y, float z, int light, boolean[] opaque) {
        Vertex v1, v2, v3, v4;
        face.setLight(lr-sh*(opaque[3]||opaque[4]||opaque[5]?1:0), light);
        v1 = new Vertex(x, y + 1, z + 1, face.r, face.g, face.b, face.minU, face.minV);
        face.setLight(lr-sh*(opaque[1]||opaque[2]||opaque[3]?1:0), light);
        v2 = new Vertex(x, y, z + 1, face.r, face.g, face.b, face.minU, face.maxV);
        face.setLight(lr-sh*(opaque[7]||opaque[0]||opaque[1]?1:0), light);
        v3 = new Vertex(x, y, z, face.r, face.g, face.b, face.maxU, face.maxV);
        face.setLight(lr-sh*(opaque[5]||opaque[6]||opaque[7]?1:0), light);
        v4 = new Vertex(x, y + 1, z, face.r, face.g, face.b, face.maxU, face.minV);
        return new Vertex[]{v1, v2, v3, v4};
    }

    public static Vertex[] getRightWithAO(Face face, float x, float y, float z, boolean[] opaque) {
        return getRightWithAO(face, x, y, z, 15, opaque);
    }

    public static Vertex[] getRightWithAO(Face face, float x, float y, float z, int light, boolean[] opaque) {
        Vertex v1, v2, v3, v4;
        face.setLight(lr-sh*(opaque[5]||opaque[6]||opaque[7]?1:0), light);
        v1 = new Vertex(x + 1, y + 1, z, face.r, face.g, face.b, face.minU, face.minV);
        face.setLight(lr-sh*(opaque[7]||opaque[0]||opaque[1]?1:0), light);
        v2 = new Vertex(x + 1, y, z, face.r, face.g, face.b, face.minU, face.maxV);
        face.setLight(lr-sh*(opaque[1]||opaque[2]||opaque[3]?1:0), light);
        v3 = new Vertex(x + 1, y, z + 1, face.r, face.g, face.b, face.maxU, face.maxV);
        face.setLight(lr-sh*(opaque[3]||opaque[4]||opaque[5]?1:0), light);
        v4 = new Vertex(x + 1, y + 1, z + 1, face.r, face.g, face.b, face.maxU, face.minV);
        return new Vertex[]{v1, v2, v3, v4};
    }

    public static Vertex[] getTopWithAO(Face face, float x, float y, float z, boolean[] opaque) {
        return getTopWithAO(face, x, y, z, 15, opaque);
    }

    public static Vertex[] getTopWithAO(Face face, float x, float y, float z, int light, boolean[] opaque) {
        Vertex v1, v2, v3, v4;
        face.setLight(to-sh*(opaque[5]||opaque[6]||opaque[7]?1:0), light);
        v1 = new Vertex(x, y + 1, z + 1, face.r, face.g, face.b, face.minU, face.minV);
        face.setLight(to-sh*(opaque[7]||opaque[0]||opaque[1]?1:0), light);
        v2 = new Vertex(x, y + 1, z, face.r, face.g, face.b, face.minU, face.maxV);
        face.setLight(to-sh*(opaque[1]||opaque[2]||opaque[3]?1:0), light);
        v3 = new Vertex(x + 1, y + 1, z, face.r, face.g, face.b, face.maxU, face.maxV);
        face.setLight(to-sh*(opaque[3]||opaque[4]||opaque[5]?1:0), light);
        v4 = new Vertex(x + 1, y + 1, z + 1, face.r, face.g, face.b, face.maxU, face.minV);
        return new Vertex[]{v1, v2, v3, v4};
    }

    public static Vertex[] getBottomWithAO(Face face, float x, float y, float z, boolean[] opaque) {
        return getBottomWithAO(face, x, y, z, 15, opaque);
    }

    public static Vertex[] getBottomWithAO(Face face, float x, float y, float z, int light, boolean[] opaque) {
        Vertex v1, v2, v3, v4;
        face.setLight(bo-sh*(opaque[3]||opaque[4]||opaque[5]?1:0), light);
        v1 = new Vertex(x, y, z, face.r, face.g, face.b, face.minU, face.minV);
        face.setLight(bo-sh*(opaque[1]||opaque[2]||opaque[3]?1:0), light);
        v2 = new Vertex(x, y, z + 1, face.r, face.g, face.b, face.minU, face.maxV);
        face.setLight(bo-sh*(opaque[7]||opaque[0]||opaque[1]?1:0), light);
        v3 = new Vertex(x + 1, y, z + 1, face.r, face.g, face.b, face.maxU, face.maxV);
        face.setLight(bo-sh*(opaque[5]||opaque[6]||opaque[7]?1:0), light);
        v4 = new Vertex(x + 1, y, z, face.r, face.g, face.b, face.maxU, face.minV);
        return new Vertex[]{v1, v2, v3, v4};
    }
}
