package com.example.marlon.theguardiannews

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.json.JSONArray
import java.util.ArrayList


// the fragment initialization parameters
private const val KEY_SECTION = "section key 1"
private const val KEY_SECTION2 = "section key 2"
private const val KEY_SECTION3 = "section key 3"
private const val KEY_END = "End query key"
private const val ARG_PARAM1 = "new"

class NewListFragment : Fragment(),NewsListAdapter.SelectedNew {
    override fun openNew(new: New) {
        val bundle=Bundle()
        bundle.putParcelable(ARG_PARAM1,new)
        val intent = Intent(this.context, NewActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    // Url first part to request data to the API
    private var urlQueryPart1: String? = null
    //  Result request  page
    private var page: Int = 0
    // Url second part to request data to the API
    private var urlQueryPart2: String? = null
    // Word to search
    private var urlQueryPart3: String? = null
    // End of url parts to request data
    private var urlQueryEnd: String? = null
    // Final url to send to request
    private var urlQuery: String? = null
    lateinit var newsJsonArray: JSONArray
    private var news: ArrayList<New> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            urlQueryPart1 = it.getString(KEY_SECTION)
            urlQueryPart2 = it.getString(KEY_SECTION2)
            urlQueryPart3 = it.getString(KEY_SECTION3)
            urlQueryEnd = it.getString(KEY_END)
        }
        news.add(New("Sport",
                "Serena Williams and other parents proving age is no barrier at Wimbledon",
                "https://gu.com/p/9xm7v",
                "https://media.guim.co.uk/b7f67ddac501506439277eaf9998f257fb9daee3/0_0_1365_819/500.jpg",
                "In July 2016, a tweet from a TV presenter made me laugh: “How the hell do you win Wimbledon with a baby in your house?” Andy Murray had just beaten Milos Raonic in straight sets, to claim his second Wimbledon title, just five months after his daughter Sophia had been born. Most parents at this stage are semi-functioning zombies; Murray was on the hottest streak of his life and would imminently go on to win Olympic gold and reach No 1 in tennis’s world rankings. That tweet could now be updated. “How the hell do you make the final of Wimbledon,” it would read, “when you’ve only just stopped breastfeeding?” Despite her subdued performance in the final against an inspired Angelique Kerber, Serena Williams’s return to tennis and her run at Wimbledon this past fortnight is simply one of the most preposterous stories in sport. In life, really. Not only is she just shy of turning 37, but last September she gave birth by caesarian section to a daughter, Alexis Olympia Ohanian Jr. Williams had life-threatening, post‑delivery complications, including a pulmonary embolism and had multiple operations. She was bedridden for the first six weeks when she returned from hospital. Williams arrived in Wimbledon ranked 181 in the world and uncharacteristically fallible. She lost two of her first four matches when she came back in March, then pulled out of the French Open with a pectoral injury. Unstoppable for much of her career, after she won the semi-final at Wimbledon on Thursday, beating Julia Görges 6-2, 6-4, she pointed out: “This is not inevitable for me.” Some will be cynical about Murray and Williams: certainly they have resources that many parents do not. On the recent five-part HBO documentary Being Serena, she was shown travelling the world with a cook and a staff of helpers to look after Alexis Olympia. Similarly, you would imagine that the night before playing Raonic, Murray could have got away with a pass to sleep in the spare room with earplugs in. But Murray and Williams leave you in no doubt that they are devoted and very involved parents. And especially Williams, who has been unusually open during interviews this Wimbledon and honest about the difficulties she has encountered as a new mother. A good chunk of Being Serena, which follows her comeback, focuses on for how long she should breastfeed. She is clearly torn, and sometimes downhearted, about whether work or parenting should come first. She works like a demon to get back in shape, goes on a strict vegan diet to lose weight: “Not French‑fry‑eating vegan,” she has clarified. Considering that she is a woman with career earnings of something like $85m, this makes for surprisingly relatable television. Williams clearly hopes that her return will be an inspiration. “This is about people – male, female, black, white, rich, poor – that are on the floor and don’t know if they can get up and do anything,” her agent Jill Smoller said this week. “And it’s about nothing is impossible.” This, in a way, has been a theme of Wimbledon 2018: if you stick in there long enough, keep on plugging away, rewards will come. This was equally true on the men’s side, where – for the first time in the Open-era, grand slam history – all four semi-finalists were over 30 years old: Novak Djokovic, 31; Rafael Nadal, 32; Kevin Anderson, 32; and John Isner, 33. Those last two players, who waged a never-ending slugfest on Friday lunchtime/afternoon/tea-time/evening have proved to be especially resilient. Both were making their 10th appearance at Wimbledon; both have disproved the perception that they are journeymen servebots. A lot of world-class athletes make what they do seem laughably, infuriatingly easy. There are many examples in tennis, such as John McEnroe who played most of his career without a coach. Only right at the end did he even start training off court and doing drills on court. “It’s always been difficult for me because I never really did any of that stuff,” he told the New York Times in 1992, not long before he retired. But, if there’s been a message from this year’s Wimbledon, it’s that talent or physical advantage only gets you so far. Even the greatest have to struggle. And the greatest, as Ali showed, as Williams is now showing, are defined by how they respond to adversity. That tennis players are sticking around for longer, peaking later is not news. The average age of the top 100 men’s players increased from 24.6 in 1990 to 28.6 in 2017, the Economist calculated last year. On the women’s side the average age of the top 100 was 25.9 in 2017, up from 22.8. It’s hard to give precise reasons why, but there’s certainly a financial element. When McEnroe won Wimbledon in 1984, he earned £100,000; this year’s winners, both male and female, receive £2.25m. That’s what pays for your team of coaches, nutritionists, fitness trainers and also, you’d imagine, supplies a motivation to keep going. Longer careers have, among other changes, led to some more fluid childcare arrangements. This year there were six mothers in the women’s draw, including Williams and Victoria Azarenka, who is contesting the mixed doubles final on Sunday afternoon with her playing partner Jamie Murray. On the men’s side, there were 20 fathers in the field, among them Roger Federer, who has two sets of twins, and Djokovic, whose second child was born in September. Wimbledon has had a staffed nursery since 1983, but it has never been busier. Having children should not be advantageous for an athlete: they shift your priorities, you hurt your back in freak bath-drawing accidents. But there’s been little evidence of the top players being adversely affected. Still, while the rewards for the players are outsized, we are starting to realise that the sacrifices are significant, too. Never was that clearer than when at the end of the first week, Williams tweeted about her daughter: “She took her first steps … I was training and missed it. I cried.”"))
    }

    fun createUrl() {
        page++
        urlQuery = if (urlQueryPart3 == null) {
            urlQueryPart1 + page + urlQueryEnd
        } else {
            urlQueryPart1 + page + urlQueryPart2 + urlQueryPart3 + urlQueryEnd
        }
    }

    private lateinit var viewManager: LinearLayoutManager

    private lateinit var viewAdapter: NewsListAdapter

    private var recyclerView: RecyclerView?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_new_list, container, false)
        viewManager = LinearLayoutManager(this.context)
        // Sets data to the recycler view
        viewAdapter = NewsListAdapter(news, this)
        // Divides the data in categories and send to the corresponding view page
        recyclerView = view.findViewById<RecyclerView>(R.id.news_list_view).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)
            // use a linear layout manager
            layoutManager = viewManager
            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                NewListFragment().apply {
                    arguments = Bundle().apply {
                        putString(KEY_SECTION, urlQueryPart1)
                        putString(KEY_SECTION, urlQueryPart2)
                        putString(KEY_SECTION, urlQueryPart3)
                        putString(KEY_SECTION, urlQueryEnd)

                    }
                }
    }
}
