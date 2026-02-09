package data;

public class CamGenData extends CamTrainData {

    public String camGenGenerationMode = "img2img";
    public String camGenModel = "Diffusion Model v1-5";
    public String camGenCollection = "otokar";
    public String camGenFromClass = "original";
    public String camGenToClass = "generative";
    public String camGenPromt = "bus chassis structure, visible skeleton, sharp focus, no blur";
    public int camGenGenerativePerImage = 1;
}
